/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.huawei.demo4mt;

import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.demo4mt.controller.Controller;

public class MyClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyClient.class);

    private static Controller controller;

    public static void main(String[] args) {
        init();

        run();
    }

    private static void run() {
        controller = BeanUtils.getBean("controller");
        int index = 2;
        int result = 0;
        while (true) {
            result = controller.add(3, index);
            TestMgr.check(result, index + 3);
            System.out.println("---------" + result);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static void init() {
        try {
            Log4jUtils.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BeanUtils.init();
    }
}
