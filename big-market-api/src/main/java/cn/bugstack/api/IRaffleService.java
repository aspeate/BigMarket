package cn.bugstack.api;

import cn.bugstack.api.dto.RaffleAwardListRequestDTO;
import cn.bugstack.api.dto.RaffleAwardListResponseDTO;
import cn.bugstack.api.dto.RaffleRequestDTO;
import cn.bugstack.api.dto.RaffleResponseDTO;
import cn.bugstack.types.model.Response;

import java.util.List;

// 抽奖接口
public interface IRaffleService {

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
    Response<RaffleResponseDTO> randomRaffle(RaffleRequestDTO requestDTO);
}
