package cn.bugstack.domain.activity.service.rule.chain;



public abstract class AbstractActionChain  implements IActionChain{

    protected IActionChain next;
    @Override
    public IActionChain appendNext(IActionChain next) {
        this.next = next;
        return next;
    }

    @Override
    public IActionChain next() {
        return next;
    }

}
