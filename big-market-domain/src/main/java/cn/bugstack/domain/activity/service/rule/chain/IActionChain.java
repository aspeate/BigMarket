package cn.bugstack.domain.activity.service.rule.chain;


import cn.bugstack.domain.activity.model.entity.ActivityCountEntity;
import cn.bugstack.domain.activity.model.entity.ActivityEntity;
import cn.bugstack.domain.activity.model.entity.ActivitySkuEntity;

public interface IActionChain extends IActionChainArmory{
    /**
     *
     * @param activitySkuEntity 活动sku实体
     * @param activityEntity 活动实体
     * @param activityCountEntity 活动次数实体
     * @return 是否通过
     */
    boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);
}
