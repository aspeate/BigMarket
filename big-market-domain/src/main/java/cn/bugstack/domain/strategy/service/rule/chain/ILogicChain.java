package cn.bugstack.domain.strategy.service.rule.chain;

import cn.bugstack.domain.strategy.service.rule.chain.factory.DefaultChainFactory;

/**
 * 责任链接口
 */
public interface ILogicChain extends ILogicChainArmory {
    /**
     *
     * @param userId 用户ID
     *  strategyId 策略ID
     * @return 奖品id
     */
    DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId);


}
