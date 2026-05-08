package cn.bugstack.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 抽奖列表请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaffleAwardListRequestDTO {
    // 策略ID
    @Deprecated
    private Long strategyId;
    // 用户ID
    private String userId;
    // 活动ID
    private Long activityId;
}
