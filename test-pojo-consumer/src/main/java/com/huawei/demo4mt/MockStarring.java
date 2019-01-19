package com.huawei.demo4mt;

import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MockStarring {

  public static void main(String[] args) throws Exception {
    Log4jUtils.init();
//    BeanUtils.init();
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
        "classpath*:META-INF/spring/*.bean.xml");
    context.start();
//    RegistryUtils.getMicroserviceInstance()

  }

}
