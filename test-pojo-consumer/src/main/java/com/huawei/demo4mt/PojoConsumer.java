package com.huawei.demo4mt;

import java.util.Arrays;
import java.util.stream.IntStream;

import javax.inject.Inject;

import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.huawei.demo4mt.codeFirstPkg.CodeFirstPojoClient;
import com.huawei.demo4mt.helloworldIntf.HelloWorld;

@Component
public class PojoConsumer {

    public static final Logger LOGGER = LoggerFactory.getLogger(PojoConsumer.class);

    public static CodeFirstPojoClient codeFirstPojoClient;

    @Inject
    public void setCodeFirstPojoClient(CodeFirstPojoClient codeFirstPojoClient) {
        PojoConsumer.codeFirstPojoClient = codeFirstPojoClient;
    }

    @RpcReference(microserviceName = "pojoprovider", schemaId = "demo4mt")
    public HelloWorld helloworld;

    static final byte buffer[] = new byte[1024];

    static {
        Arrays.fill(buffer, (byte) 1);
    }

    public static void main(String[] args) throws Exception {
        String property1 = System.getProperty("user.dir");
        LOGGER.warn("user.dir------------>" + property1);

        Log4jUtils.init();
        BeanUtils.init();
        ApplicationContext ctx = BeanUtils.getContext();
        String[] beanNamesForAnnotation = ctx.getBeanDefinitionNames();
        LOGGER.warn("beanNamesForAnnotation length--------------->" + beanNamesForAnnotation.length);
        for (String bn : beanNamesForAnnotation) {
            LOGGER.warn("[" + bn + "]");
        }
        //        runTest();
//        runLBTest();
        //        TestMgr.summary();
    }

    private static void runLBTest() {
        String microserviceName = "pojoprovider";
        codeFirstPojoClient.testLBCase(microserviceName);
    }

    private void runTest() {
        test1();
        test2();

    }

    private void test2() {
        String microserviceName = "pojoprovider";
        codeFirstPojoClient.testCodeFirstAll(microserviceName);
    }

    /**
     * Test contextClassLoader
     */
    private void test1() {
        IntStream.range(0, 100).forEach(s -> {
            if (Thread.currentThread().getName().equals("main")) {
                return;
            }
            Thread.currentThread().setContextClassLoader(null);
            LOGGER.info("------>" + Thread.currentThread().getName());
            TestMgr.check(null, helloworld.testCtClsLoader(3));
        });
    }
}
