package com.huawei.demo4mt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;

public class PojoProvider {
  public static void main(String[] args) throws Exception {
    Log4jUtils.init();
    BeanUtils.init();
    System.out.println("xxxxx");
    testConcurrentMap();
  }

  private static String testConcurrentMap() {
    Map<String, String> map = new ConcurrentHashMap<>();
    String k = "xx";
    String value = map.get(k);
    if (value == null) {
      // 这里对computeValue(k)的重复调用不敏感 
      String newValue = computeValue(k);
      value = map.putIfAbsent(k, newValue);
      if (value == null) {
        return newValue;
      }
      return value;
    }
    return value;
  }

  private static String computeValue(String k) {
    return k + "hi";
  }
}
