package com.huawei.demo4mt;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huawei.demo4mt.codeFirstPkg.CodeFirstPojoClientIntf;
import com.huawei.demo4mt.entity.Person;
import com.huawei.demo4mt.helloworldIntf.HelloWorld;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt
 */
@RestSchema(schemaId = "pojoPerfStats")
@RequestMapping(path = "/pojoPerfStats")
public class PojoPerfStats {
  public static final Logger LOGGER = LoggerFactory.getLogger(PojoConsumer.class);

  @RpcReference(microserviceName = "pojoprovider", schemaId = "demo4mt")
  public HelloWorld helloworld;

  @RpcReference(microserviceName = "pojoprovider", schemaId = "com.huawei.demo4mt.codeFirstIntf.CodeFirstPojoIntf")
  public CodeFirstPojoClientIntf codeFirstAnnotation;

  @GetMapping(path = "/testPerfCase")
  public void testPerfCase(String microserviceName) {

    try {

      Person person = new Person();
      person.setName("m000416667");
      person.setAge(20);

      Person resp = codeFirstAnnotation.sayHello(person);
      LOGGER.info("resp is:------------>" + resp.toString());
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}