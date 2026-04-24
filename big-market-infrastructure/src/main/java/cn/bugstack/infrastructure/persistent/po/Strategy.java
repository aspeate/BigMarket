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
public class Strategy {

    /** 主键id */
    private Long id;
    /** 抽奖策略id */
    private Long strategyId;
    /** 抽奖策略描述 */
    private String strategyDesc;
    /** 策略模型 */
    private String ruleModels;
    /** 创建时间 */
    private Date createTime;
    /** 更改时间 */
    private Date updateTime;
}

