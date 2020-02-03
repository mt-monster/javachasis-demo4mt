package com.huawei.vertx4spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

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
public class BookAsyncServiceImpl implements BookAsyncService {
    @Autowired
    BookService bookService;

    @Override public void add(Book book, Handler<AsyncResult<Book>> resultHandler) {
        Book saved = bookService.save(book);
        Future.succeededFuture(saved).setHandler(resultHandler);
    }

    @Override public void getAll(Handler<AsyncResult<List<Book>>> resultHandler) {
        List<Book> books = bookService.getAll();
        Future.succeededFuture(books).setHandler(resultHandler);
    }
}
