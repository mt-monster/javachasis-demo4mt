package com.huawei.demo4mt.niopkg;

import java.util.Random;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.niopkg
 */
public class TestMain {

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override public void run() {
                try {
//                    ServerNormal.start();
                    ServerBetter.start(12346);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(100);
        // TODO: 2018/10/14 add oper_expression
        char operators[] = {'+', '-', '*', '/' };
        Random random = new Random(System.currentTimeMillis());
        new Thread(new Runnable() {
            @Override public void run() {
                while (true) {
                    String expression =
                            random.nextInt(10) + "" + operators[random.nextInt(4)] + (random.nextInt(10) + 1);
                    Client.send(expression);
                    try {
                        Thread.currentThread().sleep(random.nextInt(1000));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
