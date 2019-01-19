package com.huawei.demo4mt.threadPkg;

import org.apache.servicecomb.serviceregistry.task.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class TestTask2 extends AbstractTask {

    private static Logger logger = LoggerFactory.getLogger(TestTask1.class);

    public TestTask2(EventBus eventBus) {
        super(eventBus);
    }


    @Subscribe
    protected void ListenTask1(TestTask1 testTask1) {
        if (testTask1.taskStatus == TaskStatus.FINISHED) {
            this.taskStatus = TaskStatus.READY;
        }
    }


    @Override
    protected void doRun() {
        this.taskStatus = TaskStatus.FINISHED;
        logger.info(Thread.currentThread().getName() + " Thread [2] finish: ---" + "read the TestTask2 status is " + taskStatus);
    }
}
