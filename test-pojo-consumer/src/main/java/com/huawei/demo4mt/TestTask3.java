package com.huawei.demo4mt;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.apache.servicecomb.serviceregistry.task.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTask3 extends AbstractTask {
    private static Logger logger = LoggerFactory.getLogger(TestTask3.class);

    public TestTask3(EventBus eventBus) {
        super(eventBus);
    }

    @Subscribe
    protected void ListenTask2(TestTask1 testTask2) {
        if (testTask2.taskStatus == TaskStatus.FINISHED) {
            this.taskStatus = TaskStatus.READY;
        }
    }

    @Override
    protected void doRun() {
        logger.info(Thread.currentThread().getName() + " Thread [3] finish: ---" + "read the TestTask3 status is " + taskStatus);
        try {
//            Thread.sleep(2000);
            logger.info("thread 3 is sleeping for 2 sec");
            this.taskStatus = TaskStatus.FINISHED;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
