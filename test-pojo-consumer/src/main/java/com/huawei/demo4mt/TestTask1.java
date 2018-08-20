package com.huawei.demo4mt;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.apache.servicecomb.serviceregistry.task.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTask1 extends AbstractTask {

    private static Logger logger = LoggerFactory.getLogger(TestTask1.class);

    public TestTask1(EventBus eventBus) {
        super(eventBus);
        this.taskStatus = TaskStatus.READY;
    }
    @Subscribe
    protected void ListenTask3(TestTask3 testTask3) {
        if (testTask3.taskStatus == TaskStatus.FINISHED) {
            this.taskStatus = TaskStatus.READY;
        }
    }

    @Override
    protected void doRun() {
        this.taskStatus = TaskStatus.FINISHED;
        logger.info(Thread.currentThread().getName() + " Thread [1] finish: ---" + "read the TestTask1 status is " + taskStatus);
    }
}
