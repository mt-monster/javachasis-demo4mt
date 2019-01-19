package com.huawei.demo4mt.NioPakage;

import java.util.Scanner;

import com.huawei.demo4mt.AioPkg.AioClient;
import com.huawei.demo4mt.AioPkg.AioServer;

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
        System.out.println("choose which mode to sprmigrate... 1:Nio,2:Aio");
        Boolean opt = "1".equals(new Scanner(System.in).nextLine()) ? true : false;
        if (opt) {
            NioServer.start();
            Thread.sleep(2000);
            NioClient.start();
            while (NioClient.sendMsg(new Scanner(System.in).nextLine()));
        }
        AioServer.start();
        Thread.sleep(2000);
        AioClient.start();
        while (AioClient.sendMsg(new Scanner(System.in).nextLine()));
    }
}
