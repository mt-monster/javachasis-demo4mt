package com.huawei.paas.demo;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.paas.demo
 */
@SpringBootApplication
//@ImportResource(value = {"classpath*:META-INF/spring/*bean.xml"})
//@EnableServiceComb
public class SpringMvcDemo {

    private Logger logger =  LoggerFactory.getLogger(SpringMvcDemo.class);

    @Value("${kafka.port:8888}")
    private String port;

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcDemo.class);
    }
    @Bean
    CommandLineRunner runner() {
        return arg -> {
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(5);
                    logger.error("port = {}.", port);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
    }
}
