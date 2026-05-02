package cn.bugstack.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/**
 * 策略奖品
 */
public class StrategyAwardEntity {

    /** 抽奖策略id */
    private Long strategyId;
    /** 奖品id */
    private Integer awardId;
    /** 奖品名称 */
    private String awardTitle;
    /** 奖品副标题 */
    private String awardSubtitle;
    /** 奖品库存 */
    private Integer awardCount;
    /** 奖品剩余库存 */
    private Integer awardCountSurplus;
    /** 抽奖率 */
    private BigDecimal awardRate;
    /** 奖品排序 */
    private Integer sort;
}
