package com.huawei.demo4mt.nioPkg;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.nioPkg
 */
public class Meng {
    public void answerTime(CallbackIntf callback , String name) {
        System.out.println("receive {} ask, let me calucate Time .." + name);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {

        }
        long end = System.currentTimeMillis();
        callback.calTime(end-begin);
    }
}
