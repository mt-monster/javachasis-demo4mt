package com.huawei.demo4mt;

import java.util.stream.IntStream;

import javax.inject.Inject;

import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.huawei.demo4mt.codeFirstPkg.CodeFirstPojoClient;


@Component
public class PojoConsumer {

  private static Logger logger = LoggerFactory.getLogger(PojoConsumer.class);

  @RpcReference(microserviceName = "pojoprovider", schemaId = "demo4mt")
  public static HelloWorld helloworld;

  @Inject
  public void setCodeFirstPojoClient(CodeFirstPojoClient codeFirstPojoClient) {
    PojoConsumer.codeFirstPojoClient = codeFirstPojoClient;
  }

  public static CodeFirstPojoClient codeFirstPojoClient;


  public static void main(String[] args) throws Exception {
    Log4jUtils.init();
    BeanUtils.init();
    String envProperty = System.getProperty("user.dir");
    logger.warn("envProperty is-------------->>"+envProperty);
    String[] beans = BeanUtils.getContext().getBeanDefinitionNames();
    for (String bn : beans) {
      logger.info("[" + bn + "}");
    }
    logger.info("------->" + beans.length);
    runTest();
  }

  private static void runTest() {
    buildTest1();
    buildTest2();
  }

  /**
   * test classLoader
   */
  private static void buildTest1() {
    IntStream.range(0, 100).parallel().forEach(s -> {
      if ("main".equals(Thread.currentThread().getName())) {
        return;
      }
      Thread.currentThread().setContextClassLoader(null);
      TestMgr.check(null, helloworld.testContextClsLoader(3));
    });
  }

  private static void buildTest2() {
    String microserviceName = "pojoprovider";
    codeFirstPojoClient.testCodeFirstAll(microserviceName);
  }

  public static void testSayHi() {
    while (true) {
      try {
        Thread.sleep(2000);
        String result = helloworld.sayHello(" m00416667");
        System.out.println("-----------" + result + "-------------");
      } catch (Exception e) {
        System.out.println("------------exception-----------" + e.getMessage());
      }
    }
  }
}
