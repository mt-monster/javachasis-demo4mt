package com.huawei.demo4mt;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.servicecomb.provider.pojo.RpcSchema;
import org.apache.servicecomb.serviceregistry.RegistryUtils;
import org.apache.servicecomb.swagger.invocation.context.ContextUtils;

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
 * Package Name:com.huawei.demo4mt
 */
@RpcSchema
public class CodeFirstPojoServer implements CodeFirstPojoIntf {
    @Override public Map<String, User> testUserMap(Map<String, User> userMap) {
        return userMap;
    }

    @Override public List<User> testUserArray(List<User> users) {
        return users;
    }

    @Override public String[] testStrings(String[] input) {
        input[0] += input[0] + "0";
        return input;
    }

    @Override public byte[] testBytes(byte[] input) {
        input[0] = (byte) (input[0] + 1);
        return input;
    }

    @Override public int reduce(int a, int b) {
        return a - b;
    }

    @Override public Date addDate(Date date, long second) {
        return new Date(date.getTime() + second * 1000);
    }

    @Override public Person sayHello(Person user) {
        String instanceId = RegistryUtils.getMicroserviceInstance().getInstanceId();
        user.setName(instanceId + "hello: " + user.getName());
        return user;
    }

    @Override public String saySomething(String prefix, Person user) {
        return prefix + " " + user.getName();
    }

    @Override public String sayHi(String name) {
        ContextUtils.getInvocationContext().setStatus(202);
        return name + "sayhi,context k:" + (ContextUtils.getInvocationContext() == null ?
                "" : ContextUtils.getInvocationContext().getContext("k"));
    }

    @Override public boolean isTrue() {
        return true;
    }

    @Override public String addString(List<String> s) {
        String result = "";
        for (String x : s) {
            result += x;
        }
        return result;
    }
}
