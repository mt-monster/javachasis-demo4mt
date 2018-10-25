package com.huawei.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

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

    /**
     *
     * @param filePah
     * @return
     */
    public Map<String, Object> yaml2Properties(String filePah) {
        Map map = new HashMap();
        try {
            YamlReader reader = new YamlReader(new FileReader(filePah));
            Object object = reader.read();
            System.out.println(object);
            map = (Map) object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (YamlException e) {
            e.printStackTrace();
        }
        return map;

    }

    /**
     *
     * @param filePath
     * @param operStr
     * @param originMap
     */
    public void addProperties2Yaml(String filePath, String operStr,
            Map<String, Object> originMap) {
        HashMap<String, Object> objFi = new HashMap<>();
        HashMap<String, Object> objSe = new HashMap<>();
        HashMap<String, Object> allMap = new HashMap<>();
        objSe.put("NIWSServerListClassName",
                "org.apache.servicecomb.springboot.starter.discovery.ServiceCombServerList");
        objFi.put("ribbon", objSe);
        allMap.putIfAbsent(operStr, objFi);
        originMap.putAll(allMap);
        save2Yaml(sortMapByKey(originMap), filePath);
        logger.info("------Service Add End--------");
    }

    /**
     *
     * @param filePath
     * @param originMap
     */
    public void addCseProp2Yaml(String filePath, Map<String, Object> originMap) {
        save2Yaml(sortMapByKey(originMap), filePath);
        logger.info("------Cse Add End--------");
    }

    /**
     *
     * @param originMap
     * @param filePath
     */
    private void save2Yaml(Map<String, Object> originMap, String filePath) {
        Yaml yaml = buildCusYaml();
        try {
            yaml.dump(originMap, new FileWriter(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public Yaml buildCusYaml() {
        DumperOptions options = new DumperOptions();
        options.setAllowReadOnlyProperties(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        return yaml;
    }

    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Object> sortMap = new TreeMap<String, Object>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str2.compareTo(str1);
        }
    }
}