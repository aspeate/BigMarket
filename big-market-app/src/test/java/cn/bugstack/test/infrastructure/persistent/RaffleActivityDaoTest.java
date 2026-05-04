package cn.bugstack.test.infrastructure.persistent;

import cn.bugstack.infrastructure.persistent.dao.IRaffleActivityDao;
import cn.bugstack.infrastructure.persistent.po.RaffleActivity;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class RaffleActivityDaoTest {

    @Resource
    private IRaffleActivityDao raffleActivityDao;

    @Test
    public void test_queryRaffleActivityByActivityId() {
        log.info("开始查询活动 ID: 100301");
        RaffleActivity raffleActivity = raffleActivityDao.queryRaffleActivityByActivityId(100301L);

        if (raffleActivity == null) {
            log.warn("未查询到活动 ID: 100301 的数据，请检查数据库中是否存在该记录");
        } else {
            log.info("查询结果：{}", JSON.toJSONString(raffleActivity));
        }
    }


}