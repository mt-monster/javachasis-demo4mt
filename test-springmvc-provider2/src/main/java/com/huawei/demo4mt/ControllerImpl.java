package com.huawei.demo4mt;

import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestSchema(schemaId="controllerIntf")
@RequestMapping(path="/springmvc/controllerIntf",produces = MediaType.APPLICATION_JSON)
public class ControllerImpl {
  @GetMapping(path="/add")
  public int add(@RequestParam("a")int a, @RequestParam("a")int b){
    return 1;
  }
}
