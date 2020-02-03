package com.huawei.vertx4spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import io.vertx.core.Verticle;
import io.vertx.core.spi.VerticleFactory;

/**
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
@Component
public class SpringVerticleFactory implements VerticleFactory, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override public boolean blockingCreate() {
        return true;
    }

    @Override public String prefix() {
        return "mt4test";
    }

    @Override public Verticle createVerticle(String verticleName, ClassLoader classLoader) throws Exception {
        String clazz = VerticleFactory.removePrefix(verticleName);
        return (Verticle) applicationContext.getBean(Class.forName(clazz));
    }

    @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
