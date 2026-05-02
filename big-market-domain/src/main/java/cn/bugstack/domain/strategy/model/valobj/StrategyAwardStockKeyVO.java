package cn.bugstack.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//策略id的值对象信息
public class StrategyAwardStockKeyVO {
    /** 策略ID */
    private Long strategyId;
    /** 抽奖奖品ID */
    private Integer awardId;
}
