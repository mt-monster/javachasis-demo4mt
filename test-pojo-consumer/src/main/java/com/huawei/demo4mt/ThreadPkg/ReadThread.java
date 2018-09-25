package com.huawei.demo4mt.ThreadPkg;

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
public class ReadThread implements Runnable {
    private List<Integer> list;

    public ReadThread(List<Integer> list) {
        this.list = list;
    }

    @Override public void run() {
        for (Integer ins = 0; ins < list.size(); ins++) {
            String s = "{" + ins + "}   " + "ReadThread is :" + list.get(ins);
            System.out.println(s);
        }
    }
}
