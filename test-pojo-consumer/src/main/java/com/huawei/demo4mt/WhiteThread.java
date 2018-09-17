package com.huawei.demo4mt;

import java.util.List;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt
 */
public class WhiteThread implements Runnable {
    private List<Integer> list;

    public WhiteThread(List<Integer> list) {
        this.list = list;
    }

    @Override public void run() {
        this.list.add(10);
        String s = "WhiteThread is ing";
        System.out.println(s);
    }
}
