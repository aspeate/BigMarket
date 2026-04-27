package cn.bugstack.domain.strategy.service.raffle;

import cn.bugstack.domain.strategy.model.valobj.RuleTreeVO;
import cn.bugstack.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import cn.bugstack.domain.strategy.repository.IStrategyRepository;
import cn.bugstack.domain.strategy.service.AbstractRaffleStrategy;
import cn.bugstack.domain.strategy.service.armory.IStrategyDispatch;
import cn.bugstack.domain.strategy.service.rule.chain.ILogicChain;
import cn.bugstack.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import cn.bugstack.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import cn.bugstack.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 默认的抽奖策略实现
 */
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy {


    @Resource
    private DefaultChainFactory defaultChainFactory;

    public DefaultRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory logicFactory, DefaultTreeFactory treeFactory) {
        super(repository, strategyDispatch, logicFactory, treeFactory);
    }


    @Override
    public DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId) {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        return logicChain.logic(userId, strategyId);
    }

    @Override
    public DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId) {
        //1. 获取奖品关联的规则模型
        //动作：查询当前奖品（awardId）在特定策略（strategyId）下绑定了哪些规则模型。
        //逻辑：如果该奖品没有配置任何规则模型（返回为空），说明它是一个普通奖品，不需要经过复杂的规则过滤，直接返回原始奖品 ID。
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModel(strategyId, awardId);
        if (null == strategyAwardRuleModelVO) {
            return DefaultTreeFactory.StrategyAwardVO.builder().awardId(awardId).build();
        }
        //2. 加载完整的规则树结构
        //动作：根据上一步拿到的规则模型标识（ruleModels，这里实际上对应的是规则树的 ID），从仓储层加载整棵规则树的详细信息。
        //内容：RuleTreeVO 包含了这棵树的所有节点（RuleTreeNode）和节点之间的连线关系（RuleTreeNodeLine）。
        //健壮性：如果配置了规则树 ID 但在数据库里找不到对应的树结构，系统会抛出异常，防止逻辑错误。
        RuleTreeVO ruleTreeVO = repository.queryRuleTreeVOByTreeId(strategyAwardRuleModelVO.getRuleModels());
        if (null == ruleTreeVO) {
            throw new RuntimeException("存在抽奖策略配置的规则模型 Key，未在库表 rule_tree、rule_tree_node、rule_tree_line 配置对应的规则树信息 " + strategyAwardRuleModelVO.getRuleModels());
        }
        //3. 构建并启动决策引擎
        //工厂模式：使用 defaultTreeFactory 根据加载好的 ruleTreeVO 创建一个决策树引擎实例。这个引擎内部会解析节点和连线，准备好执行逻辑。
        //执行过滤：调用 treeEngine.process 方法，传入用户 ID、策略 ID 和原始奖品 ID。
        //结果：引擎会根据规则（如：黑名单、权重、库存等）进行流转判断，最终返回一个 StrategyAwardVO。注意： 返回的奖品 ID 可能因为规则干预而发生改变（例如：被权重规则替换成了另一个奖品）。
        IDecisionTreeEngine treeEngine = defaultTreeFactory.openLogicTree(ruleTreeVO);
        return treeEngine.process(userId, strategyId, awardId);
    }


}
