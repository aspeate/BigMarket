package cn.bugstack.test.domain;

import cn.bugstack.domain.strategy.service.armory.IStrategyArmory;
import cn.bugstack.domain.strategy.service.armory.IStrategyDispatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyArmoryTest {

    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private IStrategyDispatch strategyDispatch;


    @Test
    public void test_assembleLotteryStrategy() {
        strategyArmory.assembleLotteryStrategy(10001L);
        log.info("测试完成");
    }
    @Test
    public void test_getRandomAwardId() {
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));
        log.info("测试结果：{}", strategyDispatch.getRandomAwardId(10001L));

    }

    @Test
    public void test_getRandomAwardId_ruleWeightValue() {
        log.info("测试结果: {} - 4000 策略配置", strategyDispatch.getRandomAwardId(10001L, "4000"));
        log.info("测试结果: {} - 5000 策略配置", strategyDispatch.getRandomAwardId(10001L, "5000"));
        log.info("测试结果: {} - 6000 策略配置", strategyDispatch.getRandomAwardId(10001L, "6000"));
    }
}
