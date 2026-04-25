# 03-策略概率装配处理
实现了把strategy_award表上传到redis里,将概率变成hash结构,field从1到10000, value为奖品id,每个奖品的数量为range * 概率,range=(100 / 最小概率).

查询直接调用getStrategyAwardAssemble方法即可


# 04-策略权重概率装配
先通过strategyId,查询该策略下的所有rule_model,
看看有没有rule_weight,有的话通过strategyId和rule_weight
锁定对应的StrategyRuleEntity实体,
把实体的rule_value通过逗号和空格分割成Map<String, List<Integer>>的形式,
遍历 ruleWeightValueMap的每个key, 过滤掉不在该权重奖品列表中的奖品
调用 assembleLotteryStrategy