package com.huawei.demo4mt;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt
 */
public class TestCorepool {

    private static Logger logger = LoggerFactory.getLogger(TestCorepool.class);

    public Integer num = 0;

    ScheduledExecutorService service = new ScheduledThreadPoolExecutor(2, new ThreadFactory() {
        @Override public Thread newThread(Runnable r) {
            //                return new Thread(r, "this is from newT---" + num++);
            return new Thread(r, "this is from newT---" + num++);
        }
    });

    public static void main(String[] args) throws Exception {
        Log4jUtils.init();

        new TestCorepool().testCorepool();

        //        List<Runnable> runnables = service.shutdownNow();
        //        service.shutdown();
        //        logger.info("shutdonwn ----");
    }

    private void testCorepool() {
        logger.info("enter --------");
        service.scheduleAtFixedRate(new Runnable() {
            @Override public void run() {
                num++;
                logger.info("--------" + num);
            }
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }

}
