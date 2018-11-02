package com.huawei.demo4mt.codeFirstPkg;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.servicecomb.core.CseContext;
import org.apache.servicecomb.provider.pojo.RpcReference;

import com.huawei.demo4mt.CodeFirstPojoIntf;
import com.huawei.demo4mt.DemoConst;
import com.huawei.demo4mt.TestMgr;
import com.huawei.demo4mt.entity.User;

public class CodeFirstPojoClient {
  @RpcReference(microserviceName = "pojoprovider", schemaId = "com.huawei.demo4mt.CodeFirstPojoIntf")
  public CodeFirstPojoClientIntf codeFirstAnnotation;

  @Inject
  private CodeFirstPojoIntf codeFirstFromYml;

  @RpcReference(microserviceName = "pojoprovider")
  public CodeFirstPojoClientIntf codeFirstAnnotationWithoutSchemaId;


  public void testCodeFirstAll(String microserviceName) {
    for (String transport : DemoConst.transports) {
      CseContext.getInstance().getConsumerProviderManager().setTransport(microserviceName, transport);
      TestMgr.setMsg(microserviceName, transport);
      testAll(codeFirstAnnotation, transport);
      testAll(codeFirstAnnotationWithoutSchemaId, transport);
      testAll(codeFirstFromYml, transport);

    }
  }

  private void testAll(CodeFirstPojoIntf codeFirst, String transport) {
    testCodeFirstUserMap(codeFirst);
    testCodeFirstUserArray(codeFirst);

  }

  private void testCodeFirstUserArray(CodeFirstPojoIntf codeFirst) {

  }

  private void testCodeFirstUserMap(CodeFirstPojoIntf codeFirst) {
    User user1 = new User();
    user1.setNames(new String[]{"u1","u2"});
    User user2 = new User();
    user2.setNames(new String[]{"u3","u4"});
    HashMap<String, User> userMap = new HashMap<>();
    userMap.put("x1",user1);
    userMap.put("x2",user2);
    Map<String, User> respMap = codeFirst.testUserMap(userMap);
    TestMgr.check("u1", respMap.get("u1").getNames()[0]);
    TestMgr.check("u2", respMap.get("u1").getNames()[1]);
    TestMgr.check("u3", respMap.get("u2").getNames()[0]);
    TestMgr.check("u4", respMap.get("u2").getNames()[1]);
  }

}
