package com.huawei.demo4mt.codeFirstIntf;

import java.util.Date;

import org.springframework.http.ResponseEntity;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt
 */
public interface CodeFirstSpringmvcIntf {
    ResponseEntity<String> responseEntity(String strBody, Date date);
}
