package com.huawei.demo4mt.NioPakage;

import java.util.Scanner;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.NioPakage
 */
public class TestNio {
    public static void main(String[] args) throws Exception {
        NioServer.start();
        Thread.sleep(2000);
        NioClient.start();
        while (NioClient.sendMsg(new Scanner(System.in).nextLine()));
    }
}
