package cn.bugstack.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS("0000", "成功"),
    UN_ERROR("0001", "失败"),
    INDEX_DUP("0003", "唯一索引冲突"),
    ILLEGAL_PARAMETER("0002","非法参数"),
    STRATEGY_RULE_WEIGHT_IS_NULL("ERR_BIZ_001", "业务异常，策略规则中 rule_weight 权重规则已适用但未配置"),
    UN_ASSEMBLED_STRATEGY_ARMORY("ERR_BIZ_002", "业务异常，策略规则中 rule_weight 权重规则未适用但未配置");


    private String code;
    private String info;
}
