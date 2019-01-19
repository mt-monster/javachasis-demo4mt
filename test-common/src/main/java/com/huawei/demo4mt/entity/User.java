package com.huawei.demo4mt.entity;

import org.apache.servicecomb.foundation.common.utils.JsonUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.entity
 */
public class User {
    private String name = "nameA";

    private int age = 100;

    private int index;

    private String[] names;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", age=" + age + ", index=" + index + "]";
    }

    public String jsonString() {
        try {
            return JsonUtils.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
