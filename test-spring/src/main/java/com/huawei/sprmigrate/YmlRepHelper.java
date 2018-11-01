/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.huawei.sprmigrate;

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
            map = (Map) object;
        } catch (FileNotFoundException |YamlException e) {
            e.printStackTrace();
            logger.error("Failed to transfer yaml to Properties. The failure message is: {}", e.getMessage());
        }
        return map;

    }

    /**
     *
     * @param filePath
     * @param operStr
     * @param originMap
     */
    public void addProperties2Yaml(String filePath, String operStr,Map<String, Object> originMap) {
        HashMap<String, Object> objFi = new HashMap<>();
        HashMap<String, Object> objSe = new HashMap<>();
        HashMap<String, Object> allMap = new HashMap<>();
        objSe.put("NIWSServerListClassName",
                "org.apache.servicecomb.springboot.starter.discovery.ServiceCombServerList");
        objFi.put("ribbon", objSe);
        allMap.putIfAbsent(operStr, objFi);
        originMap.putAll(allMap);
        save2Yaml(sortMapByKey(originMap), filePath);
        logger.debug("------Service Add End--------");
    }

    /**
     *
     * @param filePath
     * @param originMap
     */
    public void addCseProp2Yaml(String filePath, Map<String, Object> originMap) {
        save2Yaml(sortMapByKey(originMap), filePath);
        logger.debug("------Cse Add End--------");
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
            logger.error("Failed to save Properties to yaml. The failure message is: {}", e.getMessage());
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