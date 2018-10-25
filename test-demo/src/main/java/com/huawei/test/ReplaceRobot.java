package com.huawei.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.test
 */
public class ReplaceRobot {

    private static final Logger logger = LoggerFactory.getLogger(ReplaceRobot.class);

    private String sdkVersion = "2.3.39";

    private String basePath;

    private String[] operAdd = new String[] {};

    private DomRepHelper domRepHelper = new DomRepHelper();

    private YmlRepHelper ymlRepHelper = new YmlRepHelper();

    public ReplaceRobot(String basePath) {
        this.basePath = basePath;
    }

    public ReplaceRobot(String basePath, String sdkVersion, String... operAdd) {
        this.basePath = basePath;
        this.sdkVersion = sdkVersion;
        this.operAdd = operAdd;
    }

    public static void main(String[] args) throws Exception {
        String sdkVersion = "2.3.39";
        String[] operAdd = {"helloprovider", "pojoService"};
        // TODO: 2018/10/25 留下交互入口
        //        String[] opers = cmdScan();
        //        operAdd = opers;
        //------------获取当前绝对路径
        String abPath = new File("").getAbsolutePath();
        logger.info("<-------root path is------> {}", abPath);
        ReplaceRobot robot = new ReplaceRobot(abPath, sdkVersion, operAdd);
        robot.process();
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