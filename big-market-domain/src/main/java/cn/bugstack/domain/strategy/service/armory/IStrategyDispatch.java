package cn.bugstack.domain.strategy.service.armory;

/**
 * 策略装配库(兵工厂),负责初始化策略计算
 */
public interface IStrategyDispatch {

    /**
     * 获取随机奖品ID
     *
     * @param strategyId 策略ID
     * @return 奖品ID
     */
    Integer getRandomAwardId(Long strategyId);

    Integer getRandomAwardId(Long strategyId,String ruleWeightValue);

    /**
     * 根据策略ID和奖品ID，扣减奖品缓存库存
     *
     * @param strategyId 策略ID
     * @param awardId 奖品ID
     * @return 扣减结果
     */
    Boolean subtractionAwardStock(Long strategyId, Integer awardId);

}
