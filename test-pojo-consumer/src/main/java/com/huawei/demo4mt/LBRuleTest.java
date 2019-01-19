//package com.huawei.mt00416667;/**
// * Created by m00416667 on 2018/2/11.
// */
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.servicecomb.provider.pojo.RpcReference;
//import org.apache.servicecomb.serviceregistry.RegistryUtils;
//import org.apache.servicecomb.serviceregistry.client.http.MicroserviceInstances;
//import org.springframework.http.MediaType;
//import org.springframework.web.client.RestTemplate;
//
//import Person;
//
///**
// * 一句话功能简述
// * 功能详细描述
// * @author m00416667
// * @version [版本号, ]
// * @see  [相关类/方法]
// * @since [产品/模块版本]
// * Package Name:com.huawei.demo4mt
// */
//public class LBRuleTest {
//
//
//
//    //测试传送文件
//    public static void testUpload(RestTemplate template, String cseUrlPrefix) throws IOException {
//        String file1Content = "hello world";
//        File file1 = File.createTempFile("upload1", ".txt");
//        FileUtils.writeStringToFile(file1, file1Content);
//
//        String file2Content = " bonjour";
//        File someFile = File.createTempFile("upload2", ".txt");
//        FileUtils.writeStringToFile(someFile, file2Content);
//
//        String expect = String.format("%s:%s:%s\n"
//                        + "%s:%s:%s",
//                file1.getName(),
//                MediaType.TEXT_PLAIN_VALUE,
//                file1Content,
//                someFile.getName(),
//                MediaType.TEXT_PLAIN_VALUE,
//                file2Content);
//        System.out.println(expect);
//        MicroserviceInstances instance =
//                RegistryUtils.findServiceInstances("ljbpojotest", "pojoprovider", "latest", "0.0.4");
//        System.out.println(instance);
//
//    }
//
//    //测试list的长度为200以上
//    public static void testList() {
//        List<Person> personList = new ArrayList<Person>();
//        Person person = new Person();
//        person.setAge(18);
//        person.setName("dabin");
//        for (int i = 0; i < 300; i++) {
//            personList.add(person);
//        }
//        System.out.println(helloworld.testList(personList));
//    }
//
//    public static void testSayHelloAgain() {
//        for (int i = 0; i < 50; i++) {
//            try {
//                System.out.println("\n**********START**********");
//                System.out.println(helloworld.SayHelloAgain(1));
//            } catch (Exception e) {
//                System.out.println("\nclal err" + e.getMessage());
//            }
//
//        }
//    }
//
//}
