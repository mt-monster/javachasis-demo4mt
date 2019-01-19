package com.huawei.demo4mt.threadPkg;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.servicecomb.foundation.common.event.EventManager;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.eventbus.EventBus;

public class TestThreadPool {

    private static Logger logger = LoggerFactory.getLogger(TestThreadPool.class);

    private ScheduledExecutorService scheduledExecutorService;

    private TestSCTask testSCTask;

    private EventBus ebus;

    TestCompositeTask testCompositeTask;

    AtomicInteger cnt = new AtomicInteger(0);

    public TestThreadPool() {

        scheduledExecutorService = new ScheduledThreadPoolExecutor(4, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
//                return new Thread(r, "this is testThread {" + cnt.getAndIncrement() + "}");
                return new Thread(r);
            }
        });
        testCompositeTask = new TestCompositeTask();
        testSCTask = new TestSCTask(testCompositeTask);
        ebus = EventManager.eventBus;

    }

    @VisibleForTesting
    public static void main(String[] args) throws Exception {
        Log4jUtils.init();
        new TestThreadPool().testThreadPool();
    }


    @VisibleForTesting
    public void testThreadPool() {
        logger.info("testThreadPool enter: xxxxxxx");
//        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                logger.info("ssss");
//            }
//        }, 1000, 1000, TimeUnit.MILLISECONDS);
        testCompositeTask.addTask(new TestTask1(ebus));
        testCompositeTask.addTask(new TestTask2(ebus));
        testCompositeTask.addTask(new TestTask3(ebus));
        testCompositeTask.run();
        scheduledExecutorService.scheduleAtFixedRate(testSCTask, 1000, 1000, TimeUnit.MILLISECONDS);

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.info(Thread.currentThread().getName() + " 2nd schedulce?");
            }
        }, 500, 500, TimeUnit.MILLISECONDS);


        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.info(Thread.currentThread().getName() + " 3rd schedulce?");
            }
        }, 500, 500, TimeUnit.MILLISECONDS);
    }

}
