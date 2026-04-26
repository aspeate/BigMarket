package cn.bugstack.test.domain;

import cn.bugstack.domain.strategy.model.entity.RaffleAwardEntity;
import cn.bugstack.domain.strategy.model.entity.RaffleFactorEntity;
import cn.bugstack.domain.strategy.service.IRaffleStrategy;
import cn.bugstack.domain.strategy.service.armory.IStrategyArmory;
import cn.bugstack.domain.strategy.service.armory.IStrategyDispatch;
import cn.bugstack.domain.strategy.service.rule.impl.RuleLockLogicFilter;
import cn.bugstack.domain.strategy.service.rule.impl.RuleWeightLogicFilter;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyArmoryTest {

    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private IStrategyDispatch strategyDispatch;
    @Resource
    private IRaffleStrategy raffleStrategy;

    @Resource
    private RuleWeightLogicFilter ruleWeightLogicFilter;
    @Autowired
    private RuleLockLogicFilter ruleLockLogicFilter;

    @Test
    public void test_assembleLotteryStrategy() {
        strategyArmory.assembleLotteryStrategy(10001L);
        log.info("测试完成");
    }
    @Test
    public void test_getRandomAwardId() {
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));

    }

    @Test
    public void test_getRandomAwardId_ruleWeightValue() {
        log.info("测试结果: {} - 4000 策略配置", strategyDispatch.getRandomAwardId(10001L, "4000"));
        log.info("测试结果: {} - 4000 策略配置", strategyDispatch.getRandomAwardId(10001L, "4000"));
        log.info("测试结果: {} - 4000 策略配置", strategyDispatch.getRandomAwardId(10001L, "4000"));
        log.info("测试结果: {} - 4000 策略配置", strategyDispatch.getRandomAwardId(10001L, "4000"));
        log.info("测试结果: {} - 4000 策略配置", strategyDispatch.getRandomAwardId(10001L, "4000"));
        log.info("测试结果: {} - 4000 策略配置", strategyDispatch.getRandomAwardId(10001L, "4000"));
        log.info("测试结果: {} - 4000 策略配置", strategyDispatch.getRandomAwardId(10001L, "4000"));
        log.info("测试结果: {} - 5000 策略配置", strategyDispatch.getRandomAwardId(10001L, "5000"));
        log.info("测试结果: {} - 5000 策略配置", strategyDispatch.getRandomAwardId(10001L, "5000"));
        log.info("测试结果: {} - 5000 策略配置", strategyDispatch.getRandomAwardId(10001L, "5000"));
        log.info("测试结果: {} - 5000 策略配置", strategyDispatch.getRandomAwardId(10001L, "5000"));
        log.info("测试结果: {} - 6000 策略配置", strategyDispatch.getRandomAwardId(10001L, "6000"));
        log.info("测试结果: {} - 6000 策略配置", strategyDispatch.getRandomAwardId(10001L, "6000"));
        log.info("测试结果: {} - 6000 策略配置", strategyDispatch.getRandomAwardId(10001L, "6000"));
        log.info("测试结果: {} - 6000 策略配置", strategyDispatch.getRandomAwardId(10001L, "6000"));
    }

    @Before
    public void setUp() {
        strategyArmory.assembleLotteryStrategy(10003L);

        ReflectionTestUtils.setField(ruleWeightLogicFilter, "userScore", 4500L);
        ReflectionTestUtils.setField(ruleLockLogicFilter, "userRaffleCount", 10L);
    }

    @Test
    public void test_performRaffle() {
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("xiaofuge")
                .strategyId(10001L)
                .build();

        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

        log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));
    }

    @Test
    public void test_performRaffle_blacklist() {
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("user003")  // 黑名单用户 user001,user002,user003
                .strategyId(10001L)
                .build();

        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

        log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));
    }

    @Test
    public void test_raffle_center_rule_lock(){
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("xiaofuge")
                .strategyId(10003L)
                .build();

        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

        log.info("请求参数: {}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果: {}", JSON.toJSONString(raffleAwardEntity));
    }
}
