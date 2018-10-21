package com.huawei.test;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

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

    private XmlFileUtils xmlUtil = new XmlFileUtils();

    private String basePath;

    private String[] sdkVersion;

    private String replaceFileName = "pom.xml";

    private List<String> relativeFiles = new ArrayList<>();

    public ReplaceRobot(String basePath, String... sdkVersion) {
        this.basePath = basePath;
        this.sdkVersion = sdkVersion;
        relativeFiles.add("");
    }

    public static void main(String[] args) throws Exception {
        String[] sdkVersion = {"java.version", "spring-cloud.version"};
        new ReplaceRobot("D:\\m00416667\\springcloud-sample\\springcloud-consumer", sdkVersion).process();
    }

    private void process() {
        // TODO: 2018/10/18 multiPath
        String filePath = relativeFiles.get(0);
        File file = new File(basePath + filePath);
        // 抓取所有pom文件
        File[] files = file.listFiles(new FileFilter() {
            public boolean accept(File subFile) {
                String filePath = subFile.getAbsolutePath();
                // 处理pom文件
                logger.debug("containFiles???   " + subFile.getName().contains(replaceFileName));
                if (subFile.getName().contains(replaceFileName)) {
                    try {
                        processReplaceFile(filePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }

        });
    }

    private void processReplaceFile(String filePath) throws Exception {
        Document doc = xmlUtil.parseFile(filePath);
        logger.debug("尝试修改节点内容中。。。");
        buildCseDependency(doc, filePath);

    }

    private void buildCseDependency(Document doc, String filePath) throws Exception {
        logger.debug("enter buildCseDependency....");

        NodeList deNode = xmlUtil.getNodeList(doc, "/project/dependencies/dependency/artifactId");
        NodeList depaNode = xmlUtil.getNodeList(doc, "/project/dependencies");

        System.out.println(deNode.getLength());
        boolean isExistCse = false;
        for (int i = 0; i < deNode.getLength(); i++) {
            Node node = deNode.item(i);
            String artiNodeText = node.getTextContent();
            logger.debug("Denode**********" + artiNodeText);
            if (artiNodeText.equals("spring-cloud-starter-eureka")) {
                Node node_temp = node.getParentNode();
                System.out.println(node_temp.getNodeName());
                System.out.println(node_temp.getTextContent());
                node_temp.getParentNode().removeChild(node_temp);
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
            vId.setTextContent("2.3.39");
            dep.appendChild(gId);
            dep.appendChild(aId);
            dep.appendChild(vId);
            depaNode.item(depaNode.getLength() - 1).appendChild(dep);
        }
        xmlUtil.saveXml(doc, filePath);

    }
}