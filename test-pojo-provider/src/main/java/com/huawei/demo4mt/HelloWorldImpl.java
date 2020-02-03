package com.huawei.demo4mt;

import org.apache.servicecomb.provider.pojo.RpcSchema;
import org.apache.servicecomb.serviceregistry.RegistryUtils;
import org.springframework.web.bind.annotation.RestController;

@RpcSchema(schemaId = "demo4mt")
public class HelloWorldImpl implements HelloWorld {


  @Override
  public String sayHello(String name) {
    String instanceId = RegistryUtils.getMicroserviceInstance().getInstanceId();
    return " -----" + instanceId + "-----say hi to " + name;
  }

  @Override
  public Object testContextClsLoader(int i) {
    return null;
  }
}
