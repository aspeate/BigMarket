package cn.bugstack.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *  抽奖奖品实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardEntity {

    /** 奖品ID */
    private Integer awardId;
    /** 奖品标题（名称） */
    private String awardTitle;
    /** 奖品配置信息 */
    private String awardConfig;
    /** 奖品排序 */
    private Integer sort;
    /** 结束时间*/
    private Date endDateTime;

}
