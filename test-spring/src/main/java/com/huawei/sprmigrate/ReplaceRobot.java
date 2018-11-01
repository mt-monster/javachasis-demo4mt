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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ReplaceRobot {

    private static final Logger logger = LoggerFactory.getLogger(ReplaceRobot.class);

    private static String sdkVersion = "2.3.39";

    private String basePath;

    private String[] operAdd = new String[] {};

    private DomRepHelper domRepHelper = new DomRepHelper();

    private YmlRepHelper ymlRepHelper = new YmlRepHelper();

    private static boolean isNeedChangeSdk = true;

    public ReplaceRobot(String basePath) {
        this.basePath = basePath;
    }

    public ReplaceRobot(String basePath, String sdkVersion, String... operAdd) {
        this.basePath = basePath;
        this.sdkVersion = sdkVersion;
        this.operAdd = operAdd;
    }

    public static void main(String[] args) throws Exception {
        logger.info("<-------BEGIN TO WORK------>");
        // ------------------留下交互入口
        logger.info("是否需要替换sdk版本号，默认替换版本号是2.3.39,请输入Y(es)/N(o)，并以回车结束....");
        judgeChangeSdk();
        String[] operAdd = {"helloprovider", "pojoService"};
        String[] opers = cmdScan();
        operAdd = opers;
        //------------获取当前绝对路径
        String abPath = new File("").getAbsolutePath();
        logger.info("<-------root path is------> {}", abPath);
        ReplaceRobot robot = new ReplaceRobot(abPath, sdkVersion, operAdd);
        robot.process();
    }

    private static void judgeChangeSdk() {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        if (s.equals("Y") || s.equals("y")) {
            sdkVersion = sdkScan();
        } else if (s.equals("N") || s.equals("n")) {
            logger.info("采用缺省的sdk版本号替换工程.......");
        } else {
            logger.error("输入的字符不合法，请重新输入......");
            judgeChangeSdk();
        }
    }

    private static String sdkScan() {
        String regex = "^[A-Za-z0-9.]+$";
//        String reg= "^[\\u4e00-\\u9fa5]{0,}$";
        logger.info("请输入要替换的SDK版本号，例如2.3.39,回车键结束输入........");
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        logger.debug("输入要替换的SDK版本号是-----" + s);
        if (!s.matches(regex)) {
            logger.error("输入的版本号包含非法字符，请重新输入.....");
            sdkScan();
        }
        return s;
    }

    private static String[] cmdScan() {
        try {
            logger.info("请输入要替换的服务名，每个服务名以逗号相隔....");
            byte[] b = new byte[1024];
            int end = System.in.read(b);
            String s = new String(b, 0, end);
            logger.info("-----" + s);
            String[] split = s.split(",");
            return split;

        } catch (IOException e) {
            logger.error(e.getMessage());
            System.exit(1);
        }

        return new String[0];
    }

    private void process() throws Exception {
        if (!(new File(basePath).isDirectory())) {
            throw new Exception("not correct path");
        }
        File file = new File(basePath);
        ArrayList<File> fileArrayList = new ArrayList<>();
        //----------crawled all pom files
        List<File> findPomFileList = DiffAllDir(file, 0, Constants.findFileName[0], fileArrayList);
        logger.info("The number of crawled pom files is ：{} ", findPomFileList.size());
        findPomFileList.stream().forEach(x -> {
            try {
                logger.debug("---------->" + x.getAbsolutePath());
                processPomReplaceFile(x.getAbsolutePath());
            } catch (Exception e) {
                logger.error("Failed to process Pom File. The failure message is: {}", e.getMessage());

            }
        });
        fileArrayList.clear();
        List<File> findSpringFileList = DiffAllDir(file, 0, Constants.findFileName[1], fileArrayList);
        logger.info("The number of crawled Spring files is ：{} ", findSpringFileList.size());

        findSpringFileList.stream().forEach(f -> {
            try {
                logger.debug("---------->" + f.getAbsolutePath());
                processSprReplaceFile(f.getAbsolutePath());
            } catch (Exception e) {
                logger.error("Failed to process Spring File. The failure message is: {}", e.getMessage());
            }
        });
        logger.info("-----All To The End-----");
    }

    private void processSprReplaceFile(String filePath) {
        logger.info("SpringYaml Path---------" + filePath);
        buildCseApplicationYml(filePath);
    }

    private void processPomReplaceFile(String filePath) {
        logger.info("Find POM Path is ---------> " + filePath);
        //-----0.parse pom files
        Document doc = domRepHelper.parseFile(filePath);
        logger.info("end parse POM content....");

        //-----1.delete eureka modules
        delModules(doc);
        logger.info("end delete eureka modules....");
        //-----2.add cse dependency
        buildCseDependency(doc);
        logger.info("end build CSE-extra pom file....");

        //-----3.save all2XMl
        domRepHelper.saveXml(doc, filePath);
        logger.info("end save allfixs to pom file....");

        logger.info("Modify POM content successfully--------->");
    }

    private void buildCseApplicationYml(String filePath) {

        Stream.of(operAdd).forEach(oper -> {
            //--------1.read the original yaml to map
            Map<String, Object> originMap = ymlRepHelper.yaml2Properties(filePath);
            //--------2.add oper config to map
            ymlRepHelper.addProperties2Yaml(filePath, oper, originMap);

        });
        //--------3.add cse config
        Map<String, Object> originMap = ymlRepHelper.yaml2Properties(filePath);
        String csePath = new File(basePath + "/cse.yml").getAbsolutePath();
        logger.debug("-------" + csePath);
        Map<String, Object> cseMap = ymlRepHelper.yaml2Properties(csePath);
        originMap.putAll(cseMap);
        ymlRepHelper.addCseProp2Yaml(filePath, originMap);
    }

    private void delModules(Document doc) {
        logger.info("begin delete eureka modules....");
        NodeList modNode = null;
        modNode = domRepHelper.getNodeList(doc, "/project/modules/module");
        for (int i = 0; i < modNode.getLength(); i++) {
            Node node = modNode.item(i);
            String modNodeText = domRepHelper.getNodeValue(node);
            if (modNodeText.equals("eureka-server")) {
                logger.debug("******* {}  ******* ", modNodeText);
                logger.debug("########  doing delete:  ");
                try {
                    domRepHelper.delNode(node);
                } catch (Exception e) {
                    logger.error("Failed to delete eureka Nodes. The failure message is: {}", e.getMessage());
                }
            }
        }
    }

    private void buildCseDependency(Document doc) {
        logger.info("begin build CSE-extra pom file....");
        NodeList deNode = null, depaNode = null;
        deNode = domRepHelper.getNodeList(doc, "/project/dependencies/dependency/artifactId");
        depaNode = domRepHelper.getNodeList(doc, "/project/dependencies");

        boolean isExistCse = false;
        for (int i = 0; i < deNode.getLength(); i++) {
            Node node = deNode.item(i);
            String artiNodeText = domRepHelper.getNodeValue(node);
            logger.debug("Denode**********" + artiNodeText);
            try {
                if (artiNodeText.equals("spring-cloud-starter-eureka")) {
                    domRepHelper.delNode(node.getParentNode());
                }
                if (artiNodeText.equals("spring-cloud-starter-eureka-server")) {
                    domRepHelper.delNode(node.getParentNode());
                }
                if (artiNodeText.equals("cse-solution-spring-cloud")) {
                    isExistCse = true;
                }
            } catch (Exception e) {
                logger.error("Failed to build CSE-extra pom file. The failure message is: {}", e.getMessage());
            }

        }
        if (isExistCse == false) {
            //------------------添加cse节点------------------
            Element dep = doc.createElement("dependency");
            Element gId = doc.createElement("groupId");
            gId.setTextContent("com.huawei.paas.cse");
            Element aId = doc.createElement("artifactId");
            aId.setTextContent("cse-solution-spring-cloud");
            Element vId = doc.createElement("version");
            vId.setTextContent(sdkVersion);
            dep.appendChild(gId);
            dep.appendChild(aId);
            dep.appendChild(vId);
            depaNode.item(depaNode.getLength() - 1).appendChild(dep);
        }
    }

    private List<File> DiffAllDir(File file, int flag, String nameFilter, ArrayList<File> fileArrayList) {
        String str = ".";
        for (int i = 0; i < flag; i++) {
            str += ".";
        }
        if (file.isFile()) {
            if (file.getName().equals(nameFilter)) {
                logger.info(file.getAbsolutePath());
                fileArrayList.add(file);
            }
            logger.info(str + file);
        } else {
            try {
                File[] temp = file.listFiles();
                for (int i = 0; i < temp.length; i++) {
                    if (temp[i].getName().equals(nameFilter)) {
                        logger.debug(temp[i].getAbsolutePath());
                        fileArrayList.add(temp[i]);
                    }
                    if (!temp[i].isFile()) {
                        DiffAllDir(temp[i], flag + 1, nameFilter, fileArrayList);
                    }
                }
            } catch (NullPointerException e) {
                logger.error("not find replacefile ", e.getMessage());
            }
        }
        return fileArrayList;
    }
}