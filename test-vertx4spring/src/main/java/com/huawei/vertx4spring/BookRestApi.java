package com.huawei.vertx4spring;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.serviceproxy.ServiceProxyBuilder;

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
public class BookRestApi extends AbstractVerticle {
    public static final Logger LOG = LoggerFactory.getLogger(BookRestApi.class);

    private BookAsyncService bookAsyncService;

    @Override public void start(Future<Void> startFuture) throws Exception {
        new ServiceProxyBuilder(vertx).setAddress(BookAsyncService.ADDRESS).build(BookAsyncService.class);
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.post("/book").handler(this::addBook);
        router.get("/books").handler(this::getBooks);
        StaticHandler staticHandler = StaticHandler.create();
        router.route().handler(staticHandler);
        vertx.createHttpServer().requestHandler(router).listen(8080, listen -> {
            if (listen.succeeded()) {
                LOG.info("BookRestApi started");
                startFuture.complete();
            } else {
                startFuture.fail(listen.cause());
            }
        });
    }

    void addBook(RoutingContext routingContext) {
        Book book = new Book(routingContext.getBodyAsJson());
        bookAsyncService.add(book, ar -> {
            if (ar.succeeded()) {
                routingContext.response().setStatusCode(HTTP_CREATED).end();
            } else {
                routingContext.fail(ar.cause());
            }

        });
    }

    void getBooks(RoutingContext routingContext) {
        bookAsyncService.getAll(ar -> {
            if (ar.succeeded()) {
                routingContext.response().setStatusCode(HTTP_OK);
                List<Book> result = ar.result();
                JsonArray array = new JsonArray(result);
                routingContext.response().end(array.encodePrettily());
            }
        });
    }
}
