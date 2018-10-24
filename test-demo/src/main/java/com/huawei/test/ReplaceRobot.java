package com.huawei.test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private String[] operAdd;

    private DomRepHelper domRepHelper = new DomRepHelper();

    private YmlRepHelper ymlRepHelper = new YmlRepHelper();

    private static Map filterMap = new HashMap();

    static {
        filterMap.putIfAbsent(Constants.findFileName[0], new pomFilter());
        filterMap.putIfAbsent(Constants.findFileName[1], new appYmlFilter());
    }

    public ReplaceRobot(String basePath) {
        this.basePath = basePath;
    }

    public ReplaceRobot(String basePath, String sdkVersion, String... operAdd) {
        this.basePath = basePath;
        this.sdkVersion = sdkVersion;
        this.operAdd = operAdd;
    }

    public static void main(String[] args) throws Exception {
        String[] operAdd = {"provider", ""};
        //获取当前绝对路径
        String abPath = new File("").getCanonicalPath();
        ReplaceRobot robot = new ReplaceRobot(abPath);
        robot.process();
    }

    private void process() throws Exception {
        if (!(new File(basePath).isDirectory())) {
            throw new Exception("not correct path");
        }

        File file = new File(basePath);
        ArrayList<File> fileArrayList = new ArrayList<>();
        // 抓取所有pom文件

        List<File> findPomFileList = DiffAllDir(file, 0, Constants.findFileName[0], fileArrayList);
        System.out.println("----------" + findPomFileList.size());
        findPomFileList.stream().forEach(x -> {
            try {
                //                processPomReplaceFile(x.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        List<File> findSpringFileList = DiffAllDir(file, 0, Constants.findFileName[1], fileArrayList);
        System.out.println("******" + findSpringFileList.size());
        findSpringFileList.stream().forEach(x -> {
            try {
                processSprReplaceFile(x.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void processSprReplaceFile(String filePath) throws Exception {
        buildCseApplicationYml(filePath);
    }

    private void processPomReplaceFile(String filePath) throws Exception {
        logger.debug("---------" + filePath);
        Document doc = domRepHelper.parseFile(filePath);
        logger.debug("尝试POM修改节点内容中....");
        delModules(doc);
        buildCseDependency(doc, filePath);
        domRepHelper.saveXml(doc, filePath);
    }

    private void buildCseApplicationYml(String filePath) throws Exception {
        Map<String, Object> allMap = ymlRepHelper.yaml2Properties(filePath);
        ymlRepHelper.testDumpWriterforyaml(filePath,allMap);

    }

    private void delModules(Document doc) throws Exception {
        logger.debug("enter delModules....");
        NodeList modNode = domRepHelper.getNodeList(doc, "/project/modules/module");
        for (int i = 0; i < modNode.getLength(); i++) {
            Node node = modNode.item(i);
            String modNodeText = domRepHelper.getNodeValue(node);
            if (modNodeText.equals("eureka-server")) {
                logger.debug("******* {}  ******* ", modNodeText);
                logger.debug("########  doing delete:  ");
                domRepHelper.delNode(node);
            }
        }
    }

    private void buildCseDependency(Document doc, String filePath) throws Exception {
        logger.debug("enter buildCseDependency....");

        NodeList deNode = domRepHelper.getNodeList(doc, "/project/dependencies/dependency/artifactId");
        NodeList depaNode = domRepHelper.getNodeList(doc, "/project/dependencies");

        boolean isExistCse = false;
        for (int i = 0; i < deNode.getLength(); i++) {
            Node node = deNode.item(i);
            String artiNodeText = domRepHelper.getNodeValue(node);
            logger.debug("Denode**********" + artiNodeText);
            if (artiNodeText.equals("spring-cloud-starter-eureka")) {
                domRepHelper.delNode(node);
            }
            if (artiNodeText.equals("spring-cloud-starter-eureka-server")) {
                domRepHelper.delNode(node);
            }
            if (artiNodeText.equals("cse-solution-spring-cloud")) {
                isExistCse = true;
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

    private List<File> DiffAllDir(File file, int flag, String nameFilter, ArrayList<File> fileArrayList)
            throws Exception {

        //        String path = file.getCanonicalPath();
        //        String fileName = path;
        //        System.out.println(fileName);
        //        File[] listFiles = file.listFiles();
        //        for (int i = 0; i < listFiles.length; i++) {
        //            String name = file.getCanonicalPath();
        //            System.out.println(name);
        //            if (name.equals(Constants.findFileName[0])) {
        //                return ((pomFilter) filterMap.get(Constants.findFileName[0])).process(file, flag, fileArrayList);
        //            }
        //            if (name.equals(Constants.findFileName[1])) {
        //                return ((appYmlFilter) filterMap.get(Constants.findFileName[1])).process(file, flag, fileArrayList);
        //            }
        //
        //        }

        String str = ".";
        for (int i = 0; i < flag; i++) {
            str += ".";
        }
        if (file.isFile()) {
            if (file.getName().contains(nameFilter)) {
                logger.debug(file.getAbsolutePath());
                fileArrayList.add(file);
            }
            logger.debug(str + file);
        } else {
            try {
                File[] temp = file.listFiles();
                for (int i = 0; i < temp.length; i++) {
                    if (temp[i].getName().contains(nameFilter)) {
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