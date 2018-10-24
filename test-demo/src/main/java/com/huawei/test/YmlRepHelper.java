package com.huawei.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.test
 */
public class YmlRepHelper {
    private static final Logger logger = LoggerFactory.getLogger(YmlRepHelper.class);

    public Map<String, Object> yaml2Properties(String filePah) throws Exception {
        YamlReader reader;
        Object object = null;
        Map map = null;
        try {
            reader = new YamlReader(new FileReader(filePah));
            object = reader.read();
            System.out.println(object);
            map = (Map) object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (YamlException e) {
            e.printStackTrace();
        }
        return map;

    }

    public void testDumpWriterforyaml(String fis, Map<String, Object> contentMap) throws Exception {

        DumperOptions options = new DumperOptions();
        options.setAllowReadOnlyProperties(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        Map<String, Object> data = new HashMap<>();
        HashMap<String, Object> objP = new HashMap<>();
        HashMap<String, String> objC = new HashMap<>();
        objC.put("NIWSServerListClassName",
                "org.apache.servicecomb.springboot.starter.discovery.ServiceCombServerList");
        objP.put("ribbon", objC);
        data.putIfAbsent("helloprovider", objP);
        contentMap.putIfAbsent("cse", data);
        yaml.dump(contentMap,new FileWriter(fis));
        System.out.println("----");
    }
}