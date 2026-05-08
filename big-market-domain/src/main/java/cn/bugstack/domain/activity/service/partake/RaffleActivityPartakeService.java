package cn.bugstack.domain.activity.service.partake;

import cn.bugstack.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import cn.bugstack.domain.activity.model.entity.*;
import cn.bugstack.domain.activity.model.valobj.UserRaffleOrderStateVO;
import cn.bugstack.domain.activity.repository.IActivityRepository;
import cn.bugstack.types.enums.ResponseCode;
import cn.bugstack.types.exception.AppException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RaffleActivityPartakeService extends AbstractRaffleActivityPartake{

    private final SimpleDateFormat dateFormatMonth = new SimpleDateFormat("yyyy-MM");
    private final SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");

    public RaffleActivityPartakeService(IActivityRepository activityRepository) {
        super(activityRepository);
    }

    @Override
    protected CreatePartakeOrderAggregate doFilterAccount(String userId, Long activityId, Date currentDate) {
        //1. 账户总额度
        ActivityAccountEntity activityAccountEntity = activityRepository.queryActivityAccountByUserId(userId, activityId);
        //额度判断
        if (null == activityAccountEntity || activityAccountEntity.getTotalCountSurplus() <= 0) {
            throw new AppException(ResponseCode.ACCOUNT_QUOTA_ERROR.getCode(), ResponseCode.ACCOUNT_QUOTA_ERROR.getInfo());
        }

        //2. 账户月额度
        String month = dateFormatMonth.format(currentDate);
        ActivityAccountMonthEntity activityAccountMonthEntity = activityRepository.queryActivityAccountMonthByUserId(userId, activityId, month);
        if (null != activityAccountMonthEntity && activityAccountMonthEntity.getMonthCountSurplus() <= 0) {
            throw new AppException(ResponseCode.ACCOUNT_MONTH_QUOTA_ERROR.getCode(), ResponseCode.ACCOUNT_MONTH_QUOTA_ERROR.getInfo());
        }

        boolean isExistAccountMonth = null != activityAccountMonthEntity;
        if (null == activityAccountMonthEntity){
            activityAccountMonthEntity = ActivityAccountMonthEntity.builder()
                    .userId(userId)
                    .activityId(activityId)
                    .month(month)
                    .monthCount(activityAccountEntity.getMonthCount())
                    .monthCountSurplus(activityAccountEntity.getMonthCount())
                    .build();
        }

        //3. 账户日额度
        String day = dateFormatDay.format(currentDate);
        ActivityAccountDayEntity activityAccountDayEntity = activityRepository.queryActivityAccountDayByUserId(userId, activityId, day);
        if (null != activityAccountDayEntity && activityAccountDayEntity.getDayCountSurplus() <= 0) {
            throw new AppException(ResponseCode.ACCOUNT_DAY_QUOTA_ERROR.getCode(), ResponseCode.ACCOUNT_DAY_QUOTA_ERROR.getInfo());
        }
        boolean isExistAccountDay = null != activityAccountDayEntity;
        if (null == activityAccountDayEntity){
            activityAccountDayEntity = ActivityAccountDayEntity.builder()
                    .userId(userId)
                    .activityId(activityId)
                    .day(day)
                    .dayCount(activityAccountEntity.getDayCount())
                    .dayCountSurplus(activityAccountEntity.getDayCount())
                    .build();
        }

        //4. 构建聚合对象
        return CreatePartakeOrderAggregate.builder()
                .userId(userId)
                .activityId(activityId)
                .activityAccountEntity(activityAccountEntity)
                .isExistAccountMonth(isExistAccountMonth)
                .activityAccountMonthEntity(activityAccountMonthEntity)
                .isExistAccountDay(isExistAccountDay)
                .activityAccountDayEntity(activityAccountDayEntity)
                .build();

    }

    @Override
    protected UserRaffleOrderEntity buildUserRaffleOrder(String userId, Long activityId, Date currentDate) {
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activityId);
        return UserRaffleOrderEntity.builder()
                .userId(userId)
                .activityId(activityId)
                .activityName(activityEntity.getActivityName())
                .strategyId(activityEntity.getStrategyId())
                .orderId(RandomStringUtils.randomNumeric(12))
                .orderTime(currentDate)
                .orderState(UserRaffleOrderStateVO.create)
                .endDateTime(activityEntity.getEndDateTime())
                .build();
    }
}
