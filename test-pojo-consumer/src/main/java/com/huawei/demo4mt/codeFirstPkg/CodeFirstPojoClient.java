package com.huawei.demo4mt.codeFirstPkg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.servicecomb.core.CseContext;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.demo4mt.DemoConst;
import com.huawei.demo4mt.TestMgr;
import com.huawei.demo4mt.codeFirstIntf.CodeFirstPojoIntf;
import com.huawei.demo4mt.entity.Person;
import com.huawei.demo4mt.entity.User;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.codeFirstPkg
 */
public class CodeFirstPojoClient {
    private Logger logger=LoggerFactory.getLogger(CodeFirstPojoClient.class);

    @RpcReference(microserviceName = "pojoprovider", schemaId = "com.huawei.demo4mt.codeFirstIntf.CodeFirstPojoIntf")
    public CodeFirstPojoClientIntf codeFirstAnnotation;

    @RpcReference(microserviceName = "pojoprovider")
    public CodeFirstPojoIntf codeFirstAnnotationEmptySchemaId;

    public void testLBCase(String microserviceName) {
        CseContext.getInstance().getConsumerProviderManager().setTransport(microserviceName, "rest");

        while (true) {
            try {
                Person person = new Person();
                person.setName("m000416667");
                person.setAge(20);

                Person resp = codeFirstAnnotation.sayHello(person);
                logger.info("resp is:------------>" + resp.toString());

                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void testCodeFirstAll(String microserviceName) {
        for (String transport : DemoConst.transports) {
            CseContext.getInstance().getConsumerProviderManager().setTransport(microserviceName, transport);
            testAll(codeFirstAnnotation, transport);
            TestMgr.setMsg(microserviceName, transport);

        }
    }

    private void testAll(CodeFirstPojoIntf intf, String transport) {
        testCodeFirstUserMap(intf);
        testCodeFirstUserArray(intf);
    }

    private void testCodeFirstUserArray(CodeFirstPojoIntf intf) {
        List<User> userList = buildUsersInput();
        List<User> result = intf.testUserArray(userList);
        TestMgr.check("u1", result.get(0).getNames()[0]);
        TestMgr.check("u2", result.get(0).getNames()[1]);
        TestMgr.check("u3", result.get(1).getNames()[0]);
        TestMgr.check("u4", result.get(1).getNames()[1]);

    }

    private List<User> buildUsersInput() {
        User user1 = new User();
        user1.setNames(new String[] {"u1", "u2"});

        User user2 = new User();
        user2.setNames(new String[] {"u3", "u4"});
        ArrayList<User> list = new ArrayList<User>() {
            {
                add(user1);
                add(user2);
            }
        };
        return list;
    }

    private void testCodeFirstUserMap(CodeFirstPojoIntf intf) {
        List<User> userList = buildUsersInput();

        Map<String, User> userMap = new HashMap<>();
        for (int i = 0; i < userList.size(); i++) {
            userMap.put("u" + (i + 1), userList.get(i));
        }
        Map<String, User> result = intf.testUserMap(userMap);

        TestMgr.check("u1", result.get("u1").getNames()[0]);
        TestMgr.check("u2", result.get("u1").getNames()[1]);
        TestMgr.check("u3", result.get("u2").getNames()[0]);
        TestMgr.check("u4", result.get("u2").getNames()[1]);
    }

}
