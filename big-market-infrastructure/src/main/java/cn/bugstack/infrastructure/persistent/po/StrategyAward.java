package cn.bugstack.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAward {

    /** 主键id */
    private Long id;
    /** 抽奖策略id */
    private Long strategyId;
    /** 奖品id */
    private Integer awardId;
    /** 奖品主标题 */
    private String awardTitle;
    /** 奖品副标题 */
    private String awardSubtitle;
    /** 奖品库存 */
    private Integer awardCount;
    /** 奖品剩余库存 */
    private Integer awardCountSurplus;
    /** 抽奖率 */
    private BigDecimal awardRate;
    /** 策略模型 */
    private String ruleModels;
    /** 排序 */
    private Integer sort;
    /** 创建时间 */
    private Date createTime;
    /** 更改时间 */
    private Date updateTime;
}

