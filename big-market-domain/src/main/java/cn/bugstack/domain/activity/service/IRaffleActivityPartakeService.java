package cn.bugstack.domain.activity.service;

import cn.bugstack.domain.activity.model.entity.PartakeRaffleActivityEntity;
import cn.bugstack.domain.activity.model.entity.UserRaffleOrderEntity;

public interface IRaffleActivityPartakeService {

    /**
     * 创建订单: 用户参与抽奖活动, 扣减活动账户库存, 产生抽奖单, 如果存在没被使用的抽奖单, 则返回已经存在的抽奖单
     */
    UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);
}
