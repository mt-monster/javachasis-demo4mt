package com.huawei.dubbo.server.provide;

import com.huawei.dubbo.api.HelloServcie;

public class HelloServcieImpl implements HelloServcie {
    /**
     * 接口服务
     *
     * @param name
     * @return
     */
    @Override
    public String sayHello(String name) {
        System.out.println("hello," + name);
        return "hello," + name;
    }
}
