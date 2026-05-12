package cn.bugstack.domain.credit.service;

import cn.bugstack.domain.credit.model.entity.CreditAccountEntity;
import cn.bugstack.domain.credit.model.entity.TradeEntity;

public interface ICreditAdjustService {
    String createOrder(TradeEntity tradeEntity);

    CreditAccountEntity queryUserCreditAccount(String userId);
}
