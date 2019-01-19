package com.huawei.demo4mt;

import org.apache.servicecomb.provider.pojo.RpcSchema;
import org.apache.servicecomb.serviceregistry.RegistryUtils;

import com.huawei.demo4mt.helloworldIntf.HelloWorld;

@RpcSchema(schemaId = "demo4mt")
public class HelloWorldImpl implements HelloWorld {

    @Override public Object testCtClsLoader(int code) {
        return null;
    }

    @Override public String sayHello(String name) {
        String instanceId = RegistryUtils.getMicroserviceInstance().getInstanceId();
        return "<------" + instanceId + "say hello to:" + name;
    }

}
