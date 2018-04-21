package com.huawei.demo4mt;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.servicecomb.provider.pojo.RpcSchema;
import org.apache.servicecomb.serviceregistry.RegistryUtils;
import org.apache.servicecomb.serviceregistry.api.registry.MicroserviceInstance;
import org.springframework.web.multipart.MultipartFile;

import com.huawei.demo4mt.entity.Person;
import com.huawei.demo4mt.helloworld.greeter.HelloWorld;

@RpcSchema(schemaId = "demo4mt")
public class HelloWorldImpl implements HelloWorld {

    public String _fileUpload(MultipartFile file1, Part file2) {
        try {
            InputStream is1 = file1.getInputStream();
            InputStream is2 = file2.getInputStream();
            String content1 = IOUtils.toString(is1);
            String content2 = IOUtils.toString(is2);
            return String.format("%s:%s:%s\n"
                            + "%s:%s:%s",
                    file1.getOriginalFilename(),
                    file1.getContentType(),
                    content1,
                    file2.getSubmittedFileName(),
                    file2.getContentType(),
                    content2);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<Person> testList(List<Person> list) {
        return list;
    }

    public String SayHelloAgain(int delaytime) {

        try {
            Thread.sleep(delaytime * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "\nPojo model:invoke success:instance_+:delaytime(s):" + delaytime;
    }

    @Override public String SayHello(String name) {
        //    try {
        //      Thread.sleep(5000);
        //    } catch (InterruptedException e) {
        //      // TODO Auto-generated catch block
        //      e.printStackTrace();
        //    }
        MicroserviceInstance instance = RegistryUtils.getMicroserviceInstance();
        String instanceId = instance.getInstanceId();
        return "hello" + name + " instanceId " + instanceId;
    }

    @Override public String SayHelloAgain(String name) {
        return null;
    }
}
