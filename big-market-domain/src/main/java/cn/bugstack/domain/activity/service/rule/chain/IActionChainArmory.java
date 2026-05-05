package cn.bugstack.domain.activity.service.rule.chain;


public interface IActionChainArmory {

    IActionChain appendNext(IActionChain next);

    IActionChain next();
}
