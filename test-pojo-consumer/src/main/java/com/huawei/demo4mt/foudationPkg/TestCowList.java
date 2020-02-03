package com.huawei.demo4mt.foudationPkg;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.huawei.demo4mt.threadPkg.ReadThread;
import com.huawei.demo4mt.threadPkg.WhiteThread;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt
 */
public class TestCowList {
    private void test() {
        List<Integer> asList = Arrays.asList(1, 2);
        CopyOnWriteArrayList<Integer> integers = new CopyOnWriteArrayList<>(asList);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new ReadThread(integers));
        executorService.execute(new WhiteThread(integers));
        executorService.execute(new WhiteThread(integers));
        executorService.execute(new WhiteThread(integers));
        executorService.execute(new ReadThread(integers));
        executorService.execute(new WhiteThread(integers));
        executorService.execute(new ReadThread(integers));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("copyList size:" + integers.size() + "  copyList final is:" + Arrays.asList(integers));

    }

    public static void main(String[] args) {
        new TestCowList().test();
    }
}
