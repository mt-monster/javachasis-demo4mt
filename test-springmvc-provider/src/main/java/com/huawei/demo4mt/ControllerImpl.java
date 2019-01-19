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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huawei.demo4mt.controller.Controller;
import com.huawei.demo4mt.entity.Person;

@RestSchema(schemaId = "controller")
@RequestMapping(path = "/controller", produces = MediaType.APPLICATION_JSON)
public class ControllerImpl implements Controller {
    @GetMapping(path = "/add")
    public int add(@Min(1) @RequestParam("a") int a, @Max(5) @RequestParam("b") int b) {
        return a + b;
    }

    @Override public String saySomething(String prefix, Person user) {
        return null;
    }

    @Override public String sayHi(String name) {
        return null;
    }

    @Override public String sayHei(String name) {
        return null;
    }

    @RequestMapping(path = "/sayhello/{name}", method = RequestMethod.POST)
    public String sayHello(@PathVariable("name") String name) {
        return "hello " + name;
    }
    //
    //    @RequestMapping(path = "/saysomething", method = RequestMethod.POST)
    //    public String saySomething(String prefix, @RequestBody Person user) {
    //        return prefix + " " + user.getName();
    //    }
    //
    //    @RequestMapping(path = "/sayhi", method = RequestMethod.GET)
    //    public String sayHi(HttpServletRequest request) {
    //        String[] values = request.getParameterValues("name");
    //        return "hi " + request.getParameter("name") + " " + Arrays.toString(values);
    //    }
    //
    //    @RequestMapping(path = "/sayhei", method = RequestMethod.GET)
    //    public String sayHei(@RequestHeader("name") String name) {
    //        return "hei " + name;
    //    }
}
