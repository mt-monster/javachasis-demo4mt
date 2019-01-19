package com.huawei.paas.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.paas.demo
 */
@RestController
public class HelloWorldController {

    @Value("${kafka.port:8888}")
    private String port;

    @RequestMapping("/hello")
    public String index() {
        return "Hello World: " + port;
    }
}
