package com.huawei.demo4mt.controller;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.web.bind.annotation.RequestParam;

import com.huawei.demo4mt.entity.Person;

public interface Controller {
  int add(@Min(1)@RequestParam("a")int a,@Max(5) @RequestParam("b") int b);

  String sayHi(String name);

  String saySomething(String var, Person person);
}
