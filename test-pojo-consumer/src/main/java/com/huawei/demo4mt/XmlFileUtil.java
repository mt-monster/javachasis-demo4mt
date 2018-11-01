package com.huawei.sprmigrate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * xml文件读取，增删改节点处理
 *
 */
public class XmlFileUtil {

    private static List<String> commonNodeArr = new ArrayList<String>();

    /**
     * @description 获取文件路径
     * @return String
     */
    public static String getPath(String flieName) {
        String path = "";
        path = XmlFileUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        return path + flieName;
    }

    public static NodeList getDenpenElmentByTagName(String name ,String filePath) {
        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filePath);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        NodeList root =  document.getElementsByTagName(name);
        System.out.println(root.getLength());
        System.out.println(root.item(0).getChildNodes());
        return root;
    }

    /**
     * @description 从XML文件中读取Map
     * @return String
     */
    public static String getMapByXML(String fileName) {
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        Element element = null;
        String jsonStr = "";
        try {
            db = df.newDocumentBuilder();
            Document dt = db.parse(f);
            element = dt.getDocumentElement();
            NodeList childNodes = element.getChildNodes();
            StringBuffer json = new StringBuffer("[");
            XmlFileUtil p = new XmlFileUtil();
            json = p.iterationNode(childNodes, true, "0", json);
            jsonStr = json.substring(0, json.length() - 1) + "]";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    /**
     * 迭代获取节点
     * childNodes 子节点集合
     * hasChildren 是否有子节点
     * id id值
     * json 字符串
     */
    private StringBuffer iterationNode(NodeList childNodes, boolean hasChildren, String id, StringBuffer json) {
        if (hasChildren) {
            for (int i = 1; i < childNodes.getLength() && childNodes != null; i += 2) {
                Node node = childNodes.item(i);
                json.append("{id:\"" + id + "_" + i + "\",");
                json.append("pId:\"" + id + "\",");
                json.append("name:\"" + node.getNodeName() + "\",");
                json.append("open:0,");
                json.append("lt:\"" + node.getAttributes().getNamedItem("经度").getNodeValue() + "\",");//经度
                json.append("la:\"" + node.getAttributes().getNamedItem("纬度").getNodeValue() + "\",");//纬度
                if ((node.getChildNodes()).getLength() > 1 && node.getChildNodes() != null) {
                    json.append("isParent:1},");
                    json = iterationNode(node.getChildNodes(), true, id + "_" + i, json);
                } else {
                    json.append("isParent:0},");
                }
            }
        }
        return json;
    }

    /**
     * 根据节点参数查询节点
     * @param express 节点路径
     * @param source 搜索节点源
     * @return 查询到的第一个节点
     */
    public static Node selectSingleNode(String express, Element source) {
        Node result = null;
        //创建XPath工厂
        XPathFactory xPathFactory = XPathFactory.newInstance();
        //创建XPath对象
        XPath xpath = xPathFactory.newXPath();
        try {
            result = (Node) xpath.evaluate(express, source, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
            return null;
        }
        return result;
    }

    /**
     * 添加节点
     * @param fileName 文件名
     * @param nodeFullName 添加节点相对路径
     * @param name 节点名
     * @param jd 经度
     * @param wd 纬度
     * @return 操作结果
     */
    public static String addNode(String fileName, String nodeFullName, String name, String jd, String wd) {
        //提示信息
        String msg = null;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        try {
            db = df.newDocumentBuilder();
            Document xmlDoc = db.parse(f);
            Element root = xmlDoc.getDocumentElement();
            Element node = xmlDoc.createElement(name);
            node.setAttribute("常用", "0");
            node.setAttribute("经度", jd);
            node.setAttribute("纬度", wd);

            if ("".equals(nodeFullName) || nodeFullName == null) {
                //获取同级节点
                NodeList preNode = root.getChildNodes();
                for (int i = 0; i < preNode.getLength(); i++) {
                    if (name.equals(preNode.item(i).getNodeName())) {
                        throw new Exception("存在同名地点");
                    }
                    ;
                }
                root.appendChild(node);
            } else {
                Element nodeParent = (Element) selectSingleNode(nodeFullName, root);
                //获取同级节点
                NodeList preNode = nodeParent.getChildNodes();
                for (int i = 0; i < preNode.getLength(); i++) {
                    if (name.equals(preNode.item(i).getNodeName())) {
                        throw new Exception("存在同名地点");
                    }
                    ;
                }
                nodeParent.appendChild(node);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(new DOMSource(xmlDoc), new StreamResult(new File(getPath(fileName))));
            //output(xmlDoc);
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }

    /**
     * 修改节点
     * @param fileName 文件名
     * @param nodeFullName 添加节点相对路径
     * @param name 节点名
     * @param jd 经度
     * @param wd 纬度
     * @return 操作结果
     */
    public static String updateNode(String fileName, String nodeFullName, String name, String jd, String wd) {
        //提示信息
        String msg = null;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        try {
            db = df.newDocumentBuilder();
            Document xmlDoc = db.parse(f);
            Element root = xmlDoc.getDocumentElement();
            Element node = (Element) selectSingleNode(nodeFullName, root);
            //获取同级节点
            NodeList preNode = node.getParentNode().getChildNodes();
            for (int i = 0; i < preNode.getLength(); i++) {
                if (name.equals(preNode.item(i).getNodeName())) {
                    throw new Exception("存在同名地点");
                }
                ;
            }
            xmlDoc.renameNode(node, null, name);
            node.setAttribute("经度", jd);
            node.setAttribute("纬度", wd);
			/*Element newElement = xmlDoc.createElement(name);
			newElement.setAttribute("经度",jd);
			newElement.setAttribute("纬度",wd);
			node.getParentNode().replaceChild(newElement, node);*/
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(new DOMSource(xmlDoc), new StreamResult(new File(getPath(fileName))));
            //output(xmlDoc);
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
            ;
        }
        return msg;
    }

    /**
     * 删除节点
     * @param fileName 文件名
     * @param nodeFullName 相对位置
     * @return 操作结果
     */
    public static String delNode(String fileName, String nodeFullName) {
        //提示信息
        String msg = null;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        df.setIgnoringElementContentWhitespace(true);
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        try {
            db = df.newDocumentBuilder();
            Document xmlDoc = db.parse(f);
            //获取根节点
            Element root = xmlDoc.getDocumentElement();
            //定位节点
            Element node = (Element) selectSingleNode(nodeFullName, root);
            if (node == null) {
                throw new Exception("地点已删除");
            }
            //删除节点
            Element nodeParent = (Element) node.getParentNode();
            nodeParent.removeChild(node);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(new DOMSource(xmlDoc), new StreamResult(new File(getPath(fileName))));
            //output(xmlDoc);
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }

    /**
     * 移动节点
     * @param fileName 文件名
     * @param nodeFullName 节点相对路径
     * @param targetNodeFullname 目标节点相对路径
     * @return
     */
    public static String moveNode(String fileName, String nodeFullName, String targetNodeFullname, String moveType) {
        //提示信息
        String msg = null;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        try {
            db = df.newDocumentBuilder();
            Document xmlDoc = db.parse(f);
            Element root = xmlDoc.getDocumentElement();
            Element node = (Element) selectSingleNode(nodeFullName, root);
            Element moveNode = (Element) node.cloneNode(true);
            node.getParentNode().removeChild(node);
            Element targetNode = null;
            if ("".equals(targetNodeFullname)) {
                targetNode = root;
            } else {
                targetNode = (Element) selectSingleNode(targetNodeFullname, root);
            }
            if ("inner".equals(moveType)) {
                targetNode.appendChild(moveNode);
            } else if ("prev".equals(moveType)) {

            } else if ("next".equals(moveType)) {

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(new DOMSource(xmlDoc), new StreamResult(new File(getPath(fileName))));
            //output(xmlDoc);
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }

    /**
     * 控制台输出节点
     * @param node 节点
     */
    public static void output(Node node) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            transformer.setOutputProperty("indent", "yes");
            DOMSource source = new DOMSource();
            source.setNode(node);
            StreamResult result = new StreamResult();
            result.setOutputStream(System.out);
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取常用地址
     * @param fileName 文件名
     * @return 操作结果
     */
    public static Map<String, Object> getCommonNode(String fileName) {
        //提示信息
        String msg = null;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        try {
            db = df.newDocumentBuilder();
            Document xmlDoc = db.parse(f);
            Element root = xmlDoc.getDocumentElement();
            NodeList nodes = root.getChildNodes();
            commonNodeArr.clear();
            if (nodes != null) {
                for (int i = 1; i < nodes.getLength(); i += 2) {
                    getNodeList(nodes.item(i), "3", "常用", "1");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
            ;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg", msg);
        map.put("commonNodeArr", commonNodeArr);
        return map;
    }

    /**
     * 修改地址  常用属性
     * @param fileName 文件名
     * @param fullPathArr 修改节点相对路径数组    常用属性置为1
     * @return 操作结果
     */
    public static String updateCommonNode(String fileName, String[] fullPathArr) {
        //提示信息
        String msg = null;
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        File f = new File(getPath(fileName));
        DocumentBuilder db = null;
        try {
            db = df.newDocumentBuilder();
            Document xmlDoc = db.parse(f);
            Element root = xmlDoc.getDocumentElement();
            NodeList nodes = root.getChildNodes();
            if (nodes != null) {
                for (int i = 1; i < nodes.getLength(); i += 2) {
                    getNodeList(nodes.item(i), "1", "常用", "0");
                }
            }
            for (String fullPath : fullPathArr) {
                Node n = selectSingleNode(fullPath, root);
                getNodeList(n, "2", "常用", "1");
            }
            //保存操作
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(new DOMSource(xmlDoc), new StreamResult(new File(getPath(fileName))));
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
            ;
        }
        return msg;
    }

    /**
     * 遍历修改节点属性
     * @param node 操作节点
     * @param type 1-移出属性 2-添加属性  3-根据属性值获取节点名
     * @param attr 属性名
     */
    public static void getNodeList(Node node, String type, String attr, String val) {
        Element eNode = (Element) node;
        if ("1".equals(type)) {
            if (eNode.hasAttribute(attr)) {
                eNode.setAttribute(attr, val);
            }
            NodeList nodes = node.getChildNodes();
            if (nodes != null) {
                for (int i = 1; i < nodes.getLength(); i += 2) {
                    getNodeList(nodes.item(i), type, attr, val);
                }
            }
        } else if ("2".equals(type)) {
            eNode.setAttribute(attr, val);
        } else if ("3".equals(type)) {
            if (val.equals(node.getAttributes().getNamedItem(attr).getNodeValue())) {
                commonNodeArr.add(node.getNodeName());
            }
            NodeList nodes = node.getChildNodes();
            if (nodes != null) {
                for (int i = 1; i < nodes.getLength(); i += 2) {
                    getNodeList(nodes.item(i), type, attr, val);
                }
            }
        }

    }

    public static void main(String[] args) {
        addNode("pom.xml", "groupId", "asdf", "001", "002");
        updateNode("pom.xml", "//亚洲//asdf", "wert", "555", "666");
        delNode("pom.xml", "//亚洲//wert");
        System.out.println("0000");
    }
}
