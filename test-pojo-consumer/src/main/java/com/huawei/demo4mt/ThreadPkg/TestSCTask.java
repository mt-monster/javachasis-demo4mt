package com.huawei.demo4mt.ThreadPkg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSCTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestSCTask.class);
    private TestCompositeTask testCompositeTask;

    public TestSCTask(TestCompositeTask testCompositeTask) {
        this.testCompositeTask = testCompositeTask;
    }

//    @Subscribe
//    public void onRegisterTask( task) {
//        LOGGER.info("read {} status is {}", task.getClass().getSimpleName(), task.taskStatus);
//        if (task.getTaskStatus() == TaskStatus.FINISHED) {
//            registerInstanceSuccess = true;
//        } else {
//            onException();
//        }
//    }


    @Override
    public void run() {
        try {
            testCompositeTask.run();
        } catch (Throwable e) {
            LOGGER.error("unexpected exception caught from service center task. ", e);
        }
    }
}
