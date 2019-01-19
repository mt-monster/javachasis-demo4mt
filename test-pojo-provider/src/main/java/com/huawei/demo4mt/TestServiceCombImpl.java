//package com.huawei.helloworld;
//
//import org.apache.servicecomb.provider.pojo.RpcSchema;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import io.swagger.annotations.ApiParam;
//
////@RpcSchema(schemaId="csvarraytest")
//public class TestServiceCombImpl implements TestServiceComb {
//
//
//  @RequestMapping(value="/testarrayresponse",method=RequestMethod.GET)
//  @ApiParam(collectionFormat = "csv")
//  public String[] testArrayResponse( @RequestParam String[] str) {
//    return str;
//  }
//}
