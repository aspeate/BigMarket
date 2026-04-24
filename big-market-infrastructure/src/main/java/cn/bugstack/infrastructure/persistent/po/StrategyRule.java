package cn.bugstack.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyRule {

    /** 主键id */
    private Long id;
    /** 抽奖策略id */
    private Long strategyId;
    /** 奖品id */
    private Integer awardId;
    /** 抽奖规则类型[1-策略规则、2-奖品规则] */
    private Integer ruleType;
    /** 抽奖规则类型[rule_lock] */
    private String ruleModels;
    /** 抽奖规则比值 */
    private String ruleValue;
    /** 抽奖规则描述 */
    private String ruleDesc;
    /** 创建时间 */
    private Date createTime;
    /** 更改时间 */
    private Date updateTime;
}

