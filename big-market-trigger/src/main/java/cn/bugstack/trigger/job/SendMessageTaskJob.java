package cn.bugstack.trigger.job;

import cn.bugstack.domain.task.model.entity.TaskEntity;
import cn.bugstack.domain.task.service.ITaskService;
import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component("sendMessageTaskJob")
public class SendMessageTaskJob {
    @Resource
    private ITaskService taskService;
    @Resource
    private ThreadPoolExecutor executor;
    @Resource
    private IDBRouterStrategy dbRouter;


    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        try {
            //获取分库数量
            int dbCount = dbRouter.dbCount();
            //逐个库扫描task表
            for (int dbIdx = 1; dbIdx < dbCount; dbIdx++) {
//                int finalDbIdx = dbIdx;
//                executor.execute(() -> {
                    try {
                        dbRouter.setDBKey(dbIdx);
                        dbRouter.setTBKey(0);
                        List<TaskEntity> taskEntityList = taskService.queryNoSendMessageTaskList();
                        if (null == taskEntityList) return;

                        //发送mq
                        for (TaskEntity taskEntity : taskEntityList){
//                            executor.execute(() -> {
                                try {
                                    taskService.sendMessage(taskEntity);
                                    taskService.updateTaskSendMessageCompleted(taskEntity.getUserId(), taskEntity.getMessageId());
                                }catch (Exception e){
                                    log.error("发送mq消息失败 userId:{},topic: {}",taskEntity.getUserId(), taskEntity.getTopic(), e);
                                    taskService.updateTaskSendMessageFail(taskEntity.getUserId(), taskEntity.getMessageId());
                                }
//                            });
                        }
                    } finally {
                      dbRouter.clear();
                    }
//                });
            }
        }catch (Exception e){
            log.error("定时任务,扫描mq任务列表发送消息任务失败", e);
        }

    }
}
