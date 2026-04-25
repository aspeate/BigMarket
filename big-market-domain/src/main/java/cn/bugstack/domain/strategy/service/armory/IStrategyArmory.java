package cn.bugstack.domain.strategy.service.armory;

/**
 * 策略装配库(兵工厂),负责初始化策略计算
 */
public interface IStrategyArmory {

    /**
     * 抽奖策略配置,触发的时机可以为活动审核结束后调用
     *
     * @param strategyId 策略ID
     * @return
     */
    boolean assembleLotteryStrategy(Long strategyId);
}
