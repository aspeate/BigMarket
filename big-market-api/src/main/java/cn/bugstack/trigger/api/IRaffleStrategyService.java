package cn.bugstack.trigger.api;

import cn.bugstack.trigger.api.dto.*;
import cn.bugstack.types.model.Response;

import java.util.List;

// 抽奖接口
public interface IRaffleStrategyService {

    /**
     * 策略装配接口
     * @param strategyId 策略ID
     * @return 策略配置结果
     */
    Response<Boolean> strategyArmory(Long strategyId);

    /**
     * 查询抽奖奖品列表
     * @param requestDTO 抽奖请求参数
     * @return 抽奖结果
     */
    Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO requestDTO);

    /**
     * 抽奖
     * @param requestDTO 抽奖请求参数
     * @return 抽奖结果
     */
    Response<RaffleStrategyResponseDTO> randomRaffle(RaffleStrategyRequestDTO requestDTO);

    /**
     * 查询抽奖策略权重规则，给用户展示出抽奖N次后必中奖奖品范围
     *
     * @param request 请求对象
     * @return 权重奖品配置列表「这里会返回全部，前端可按需展示一条已达标的，或者一条要达标的」
     */
    Response<List<RaffleStrategyRuleWeightResponseDTO>> queryRaffleStrategyRuleWeight(RaffleStrategyRuleWeightRequestDTO request);

}
