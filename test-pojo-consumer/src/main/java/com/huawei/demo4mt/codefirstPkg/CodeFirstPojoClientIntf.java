package com.huawei.demo4mt.codefirstPkg;

import java.util.concurrent.CompletableFuture;

import com.huawei.demo4mt.CodeFirstPojoIntf;

import io.swagger.annotations.ApiOperation;

public interface CodeFirstPojoClientIntf extends CodeFirstPojoIntf {
  @ApiOperation(nickname = "sayHi", value = "")
  CompletableFuture<String> sayHiAsync(String name);

  String sayHi2(String name);
}
