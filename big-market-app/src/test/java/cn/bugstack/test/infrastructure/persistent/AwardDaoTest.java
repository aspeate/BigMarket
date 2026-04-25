package cn.bugstack.test.infrastructure.persistent;

import cn.bugstack.infrastructure.persistent.dao.IAwardDao;
import cn.bugstack.infrastructure.persistent.po.Award;
import cn.bugstack.infrastructure.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AwardDaoTest {

    @Resource
    private IAwardDao awardDao;

    @Test
    public void test_queryAwardList() {
        List<Award> awardList = awardDao.queryAwardList();
        log.info("测试结果：{}", awardList);
    }


    @Resource
    private IRedisService redisService;

    @Test
    public void test_redis() {
        redisService.setValue("test", "hello world");
        log.info("测试结果：{}", (Object) redisService.getValue("test"));
    }


}
