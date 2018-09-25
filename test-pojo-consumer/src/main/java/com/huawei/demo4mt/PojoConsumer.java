package com.huawei.demo4mt;

import org.apache.servicecomb.core.CseContext;
import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.springframework.stereotype.Component;

import com.huawei.demo4mt.helloworld.greeter.HelloWorld;

@Component
public class PojoConsumer {

    @RpcReference(microserviceName = "pojoprovider", schemaId = "demo4mt")
    public static HelloWorld helloworld;

    public static void main(String[] args) throws Exception {
        Log4jUtils.init();
        BeanUtils.init();
        runTest();
    }

    private static void runTest() {
        String microserviceName = "pojoprovider";
        for (String transport : DemoConst.transports) {
            CseContext.getInstance().getConsumerProviderManager().setTransport(microserviceName, transport);
            testSayHi();
        }

    }

    public static void testSayHi() {
        while (true) {
            try {
                Thread.sleep(2000);
                String result = helloworld.SayHello(" dabin");
                System.out.println("-----------" + result + "-------------");
            } catch (Exception e) {
                System.out.println("------------exception-----------" + e.getMessage());
            }
        }
    }
}
