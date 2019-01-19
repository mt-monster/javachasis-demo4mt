package com.huawei.demo4mt;

import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;

public class PojoProvider {
    public static void main(String[] args) throws Exception {
        String property1 = System.getProperty("user.dir");
        System.out.println("user.dir------------>" + property1);
        Log4jUtils.init();
        BeanUtils.init();
    }
}
