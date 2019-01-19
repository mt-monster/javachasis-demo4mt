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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class TestMgr {
  private static final Logger LOGGER = LoggerFactory.getLogger(TestMgr.class);

  private static final List<Throwable> errorList = new ArrayList<>();

  private static String MSG = "";

  public void setMSG(String MSG) {
    TestMgr.MSG = MSG;
  }

  public static void setMSG(String microserviceName, String transport) {
    TestMgr.MSG = String.format("microservice=%s, transport=%s", microserviceName, transport);
  }

  public static void check(Object expect, Object real) {
    if (expect == real) {
      return;
    }

    String strExpect = String.valueOf(expect);
    String strReal = String.valueOf(real);

    if (!strExpect.equals(strReal)) {
      errorList.add(new Error(MSG + " | Expect " + strExpect + ", but " + strReal));
    }
  }

  public static void checkNotEmpty(String real) {
    if (StringUtils.isEmpty(real)) {
      errorList.add(new Error(MSG + " | unexpected null result, method is " + getCaller()));
    }
  }

  public static void summary() {
    if (errorList.isEmpty()) {
      LOGGER.info("............. sprmigrate finished ............");
      return;
    }

    LOGGER.info("............. sprmigrate not finished ............");
    for (Throwable e : errorList) {
      LOGGER.info("", e);
    }
  }

  public static List<Throwable> errors() {
    return errorList;
  }

  private static String getCaller() {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    if (stackTrace.length < 3) {
      return null;
    }
    StackTraceElement stackTraceElement = stackTrace[3];
    return stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName();
  }
}
