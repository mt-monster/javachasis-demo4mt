package com.huawei.test;

import java.io.File;
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

    public ReplaceRobot(String basePath, String... sdkVersion) {
        this.basePath = basePath;
        this.sdkVersion = sdkVersion;
    }

    public static void main(String[] args) throws Exception {
        String[] sdkVersion = {"java.version", "spring-cloud.version"};
        new ReplaceRobot("D:\\m00416667\\springcloud-sample", sdkVersion).process();
    }

    private void process() throws Exception {
        if (!(new File(basePath).isDirectory())) {
            throw new Exception("not correct path");
        }

        File file = new File(basePath);
        ArrayList<File> fileArrayList = new ArrayList<>();
        // 抓取所有pom文件
        List<File> findFileList = listAllDir(file, 0, replaceFileName, fileArrayList);
        System.out.println(findFileList.size());
        for (int i = 0; i < findFileList.size(); i++) {
            System.out.println("---------" + findFileList.get(i).getName());
            processReplaceFile(findFileList.get(i).getAbsolutePath());
        }
        findFileList.stream().forEachOrdered(x-> processReplaceFile(x.getAbsolutePath()));

    }

    private void processReplaceFile(String filePath){
        Document doc = null;
        try {
            doc = xmlUtil.parseFile(filePath);
            logger.debug("尝试修改节点内容中。。。");
            buildCseDependency(doc, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     *
     * @param file
     * @param flag
     * @param name
     * @param fileArrayList
     * @return
     */
    public List<File> listAllDir(File file, int flag, String name, ArrayList<File> fileArrayList) {
        String str = ".";
        for (int i = 0; i < flag; i++) {
            str += ".";
        }
        if (file.isFile()) {
            if (file.getName().contains(name)) {
                System.out.println(file.getAbsolutePath());
                fileArrayList.add(file);
            }
            System.out.println(str + file);
        } else {
            try {
                File[] temp = file.listFiles();
                for (int i = 0; i < temp.length; i++) {
                    if (temp[i].getName().contains(name)) {
                        System.out.println(temp[i].getAbsolutePath());
                        fileArrayList.add(temp[i]);
                    }
                    if (!temp[i].isFile()) {
                        listAllDir(temp[i], flag + 1, name, fileArrayList);
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("寻找到某些非正常文件");
            }
        }
        return fileArrayList;
    }

}