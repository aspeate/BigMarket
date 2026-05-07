package cn.bugstack.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 抽奖奖品列表,答应对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardListResponseDTO {
    //奖品ID
    private Integer awardId;
    //奖品名称
    private String awardTitle;
    //奖品副标题,抽奖几次后解锁
    private String awardSubtitle;
    //奖品排序
    private Integer sort;
}
