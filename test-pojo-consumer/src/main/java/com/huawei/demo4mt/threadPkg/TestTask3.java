package com.huawei.demo4mt.threadPkg;

import org.apache.servicecomb.serviceregistry.task.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class TestTask3 extends AbstractTask {
    private static Logger logger = LoggerFactory.getLogger(TestTask3.class);

    protected TestTask3(EventBus eventBus) {
        super(eventBus);
    }

    @Subscribe
    protected void listenTask2(TestTask1 testTask2) {
        if (testTask2.taskStatus == TaskStatus.FINISHED) {
            this.taskStatus = TaskStatus.READY;
        }
    }

    @Override
    protected void doRun() {
        try {
//            Thread.sleep(2000);
//            logger.info("thread 3 is sleeping for 2 sec");
            this.taskStatus = TaskStatus.FINISHED;
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info(Thread.currentThread().getName() + " Thread [3] finish: ---" + "read the TestTask3 status is " + taskStatus);

    }
}
