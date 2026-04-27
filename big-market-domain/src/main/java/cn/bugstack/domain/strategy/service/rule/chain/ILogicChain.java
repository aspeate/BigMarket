package cn.bugstack.domain.strategy.service.rule.chain;

/**
 * 责任链接口
 */
public interface ILogicChain extends ILogicChainArmory {
    /**
     *
     * @param userId 用户ID
     * @parm strategyId 策略ID
     * @return 奖品id
     */
    Integer logic(String userId, Long strategyId);


}
