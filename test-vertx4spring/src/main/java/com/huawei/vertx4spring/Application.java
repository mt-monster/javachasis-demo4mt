package com.huawei.vertx4spring;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

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
@SpringBootApplication
public class Application {
    public static final Logger LOG = LoggerFactory.getLogger(Application.class);

    @Autowired
    SpringVerticleFactory verticleFactory;

    @Value("${vertx.worker.pool.size}")
    int workerPoolSize;

    @Value("${vertx.springWorker.instances}")
    int springWorkerInstances;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener
    public void deployVerticles(ApplicationReadyEvent event) {
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(workerPoolSize));

        // The verticle factory is registered manually because it is created by the Spring container
        vertx.registerVerticleFactory(verticleFactory);

        CountDownLatch deployLatch = new CountDownLatch(2);
        AtomicBoolean failed = new AtomicBoolean(false);

        String restApiVerticleName = verticleFactory.prefix() + ":" + BookRestApi.class.getName();
        vertx.deployVerticle(restApiVerticleName, ar -> {
            if (ar.failed()) {
                LOG.error("Failed to deploy verticle", ar.cause());
                failed.compareAndSet(false, true);
            }
            deployLatch.countDown();
        });

        DeploymentOptions workerDeploymentOptions = new DeploymentOptions()
                .setWorker(true)
                // As worker verticles are never executed concurrently by Vert.x by more than one thread,
                // deploy multiple instances to avoid serializing requests.
                .setInstances(springWorkerInstances);
        String workerVerticleName = verticleFactory.prefix() + ":" + SpringWorker.class.getName();
        vertx.deployVerticle(workerVerticleName, workerDeploymentOptions, ar -> {
            if (ar.failed()) {
                LOG.error("Failed to deploy verticle", ar.cause());
                failed.compareAndSet(false, true);
            }
            deployLatch.countDown();
        });

        try {
            if (!deployLatch.await(10, SECONDS)) {
                throw new RuntimeException("Timeout waiting for verticle deployments");
            } else if (failed.get()) {
                throw new RuntimeException("Failure while deploying verticles");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
