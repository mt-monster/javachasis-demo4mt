package com.huawei.demo4mt.nioPkg;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ScheduledThreadPoolTest {
  public static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

  public static void main(String[] args) throws InterruptedException {
    getScheduledThreadPoolExecutor();
    scheduledThreadPoolExecutor.scheduleAtFixedRate(new testTask(), 3, 3, TimeUnit.SECONDS);
  }

  private static void getScheduledThreadPoolExecutor() {
    scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2, new ThreadFactory() {
      public Thread newThread(Runnable task) {
        return new Thread(task, "Test Task");
      }
    }, new RejectedExecutionHandler() {

      @Override
      public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
        System.out.println("Too many pending tasks, reject " + task.getClass().getName());
      }
    });
  }
}

class testTask implements Runnable {

  @Override
  public void run() {
    System.out.println("sprmigrate task");
  }
}
