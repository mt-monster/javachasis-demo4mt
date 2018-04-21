package com.huawei.demo4mt;/**
 * Created by m00416667 on 2018/3/6.
 */

import java.util.Date;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.swagger.extend.annotations.ResponseHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ResponseHeader;

//import io.servicecomb.swagger.extend.annotations.ResponseHeaders;.

//import io.servicecomb.provider.rest.common.RestSchema;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt
 */
@RestSchema(schemaId = "codeFirst")
@RequestMapping(path = "/codeFirstSpringmvc", produces = MediaType.APPLICATION_JSON_VALUE)
public class CodeFirstSpringmvcServer implements CodeFirstSpringmvcIntf{
    private static Logger logger = LoggerFactory.getLogger(CodeFirstSpringmvcServer.class);

    @ResponseHeaders( {@ResponseHeader(name = "h1", response = String.class),
            @ResponseHeader(name = "h2", response = String.class)})
    @RequestMapping(path = "/responseEntity", method = RequestMethod.POST ,produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> responseEntity(@RequestBody(required = true) String strBody,@RequestAttribute("date") Date date) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("h1", "h1v " + c1.getContext().toString());
//
//        InvocationContext c2 = ContextUtils.getInvocationContext();
//        headers.add("h2", "h2v " + c2.getContext().toString());

        logger.info("----------pre_vm_create_notify enter, strBody is " + strBody);

//        return new ResponseEntity<Date>(date, headers, HttpStatus.ACCEPTED);
        ResponseEntity resultRes=null;
        return  new ResponseEntity<String>(strBody,HttpStatus.OK);
    }
}
