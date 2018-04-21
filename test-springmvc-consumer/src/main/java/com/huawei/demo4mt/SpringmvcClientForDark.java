/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.demo4mt;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.huawei.demo4mt.controller.Controller;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author
 * @version [版本号, 2017年1月3日]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 */
public class SpringmvcClientForDark {
    private static final Logger logger = LoggerFactory.getLogger(SpringmvcClientForDark.class);

    private static RestTemplate restTemplate;

    private static Controller controller;

    public static void main(String[] args) throws Exception {
        init();

        run();
    }

    public static void run() throws Exception {
        restTemplate = RestTemplateBuilder.create();
        String prefix = "cse://springmvc";
        //test darklaunch concurrency

        CountDownLatch countDownLatch = new CountDownLatch(4);
        for (int i = 0; i < 100; i++) {
            int index = i;
            new Thread("T--case" + index) {
                @Override public void run() {
                    Object result = 10;
                    try {
                        result = controller.add(index, 3);
                        System.out.println("the result is ----" + result);
                    } catch (Exception e) {
                        logger.error("", e);
                        System.out.println("the result is ----" + result);
                        result = e.getCause().getMessage();
                    }
                    TestMgr.check(result,index+3);
                    countDownLatch.countDown();
                }
            }.start();
        }

        countDownLatch.await(10, TimeUnit.SECONDS);
        TestMgr.summary();
    }

    public static void init() throws Exception {
        Log4jUtils.init();
        BeanUtils.init();
    }

}
