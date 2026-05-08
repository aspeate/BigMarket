package cn.bugstack.domain.strategy.service.armory;

import cn.bugstack.domain.strategy.model.entity.StrategyAwardEntity;
import cn.bugstack.domain.strategy.model.entity.StrategyEntity;
import cn.bugstack.domain.strategy.model.entity.StrategyRuleEntity;
import cn.bugstack.domain.strategy.repository.IStrategyRepository;
import cn.bugstack.types.common.Constants;
import cn.bugstack.types.enums.ResponseCode;
import cn.bugstack.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 * 策略装配库(兵工厂),负责初始化策略计算
 */
@Service
@Slf4j
public class StrategyArmoryDispatch implements IStrategyArmory, IStrategyDispatch {

    @Resource
    private IStrategyRepository strategyRepository;


    @Override
    public boolean assembleLotteryStrategyByActivityId(Long activityId) {
        Long strategyId = strategyRepository.queryStrategyIdByActivityId(activityId);
        return assembleLotteryStrategy(strategyId);
    }

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        //1.根据策略ID获取策略
        List<StrategyAwardEntity> strategyAwardEntities = strategyRepository.queryStrategyAwardList(strategyId);

        //2.缓存奖品库存[用于decr扣减库存操作]
        for(StrategyAwardEntity strategyAwardEntity : strategyAwardEntities){
            Integer awardId = strategyAwardEntity.getAwardId();
            Integer awardCount = strategyAwardEntity.getAwardCount();
            cacheStrategyAwardCount(strategyId, awardId, awardCount);
        }

        //3.1默认装配, 全量抽奖概率
        assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntities);

        //3.2.权重策略,适用于rule_weight 权重规则配置
        StrategyEntity strategyEntity = strategyRepository.queryStrategyEntityByStrategyId(strategyId);
        String ruleWeight = strategyEntity.getRuleWeight();
        if (null == ruleWeight) return true;

        StrategyRuleEntity strategyRuleEntity = strategyRepository.queryStrategyRule(strategyId,ruleWeight);
        if (null == strategyRuleEntity){
            throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(),ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }
        Map<String, List<Integer>> ruleWeightValueMap = strategyRuleEntity.getRuleWeightValues();
        Set<String> keys = ruleWeightValueMap.keySet();
        for (String key : keys) {
            List<Integer> ruleWeightValues = ruleWeightValueMap.get(key);
            ArrayList<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntities);
            strategyAwardEntitiesClone.removeIf(entity -> !ruleWeightValues.contains(entity.getAwardId()));
            assembleLotteryStrategy(String.valueOf(strategyId).concat("_").concat(key), strategyAwardEntitiesClone);
        }
        return true;
    }

    private void cacheStrategyAwardCount(Long strategyId, Integer awardId, Integer awardCount) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + strategyId + Constants.UNDERLINE + awardId;
        strategyRepository.cacheStrategyAwardCount(cacheKey, awardCount);
    }

    public void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntities) {
        //1.获取最小概率
        BigDecimal minAwardRate = strategyAwardEntities
                .stream().map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        //2.获取概率总和
        BigDecimal totalAwardRate = strategyAwardEntities
                .stream().map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //3.用1 % 最小概率   获取概率范围
        BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

        //4.创建集合表,把对应的奖品id放入集合中
        ArrayList<Integer> strategyAwardSearchRateTables = new ArrayList<>(rateRange.intValue());

        for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntities) {
            BigDecimal awardRate = strategyAwardEntity.getAwardRate();
            Integer awardId = strategyAwardEntity.getAwardId();
            //计算出每个概率值需要存放到查找表的数量,循环填充
            for (int i = 0; i < rateRange.multiply(BigDecimal.valueOf(awardRate.doubleValue() / 100)).setScale(0, RoundingMode.CEILING).intValue(); i++) {
                strategyAwardSearchRateTables.add(awardId);
            }
        }

        //5.乱序
        Collections.shuffle(strategyAwardSearchRateTables);

        //6.把集合变成hashmap
        HashMap<Integer, Integer> shuffleStrategyAwardSearchRateTables = new HashMap<>(strategyAwardSearchRateTables.size());
        for (int i = 0; i < strategyAwardSearchRateTables.size(); i++) {
            shuffleStrategyAwardSearchRateTables.put(i, strategyAwardSearchRateTables.get(i));
        }

        //7.放入缓存
        strategyRepository.storeStrategyAwardSearchRateTables(key, shuffleStrategyAwardSearchRateTables.size(), shuffleStrategyAwardSearchRateTables);
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        // 分布式部署下，不一定为当前应用做的策略装配。也就是值不一定会保存到本应用，而是分布式应用，所以需要从 Redis 中获取。
        int rateRange = strategyRepository.getRateRange(strategyId);
        // 通过生成的随机值，获取概率值奖品查找表的结果
        return strategyRepository.getStrategyAwardAssemble(String.valueOf(strategyId), new SecureRandom().nextInt(rateRange));
    }

    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        String key = strategyId.toString().concat("_").concat(ruleWeightValue);
        // 分布式部署下，不一定为当前应用做的策略装配。也就是值不一定会保存到本应用，而是分布式应用，所以需要从 Redis 中获取。
        int rateRange = strategyRepository.getRateRange(key);
        // 通过生成的随机值，获取概率值奖品查找表的结果
        return strategyRepository.getStrategyAwardAssemble(key, new SecureRandom().nextInt(rateRange));
    }

    @Override
    public Boolean subtractionAwardStock(Long strategyId, Integer awardId, Date endDateTime) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + strategyId + Constants.UNDERLINE + awardId;
        return strategyRepository.subtractionAwardStock(cacheKey);
    }


}
