package com.huawei.test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.w3c.dom.Document;
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
public class XmlFileUtils {

    public XPath oXpath;

    /**
     * 构造xml文档返回对应的document对象
     * @param filename
     * @return
     * @throws Exception
     */
    public Document parseFile(String filename) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();
        XPathFactory xPathFactory = XPathFactory.newInstance();
        this.oXpath = xPathFactory.newXPath();
        return db.parse(filename);
    }

    /**
     *
     * @param node
     * @return
     */
    public String getNodeValue(Node node) {
        return node.getTextContent();
    }

    /**
     *
     * @param node
     * @param xpath
     * @return
     */
    public NodeList getNodeList(Node node, String xpath) throws XPathExpressionException {
        NodeList nodeList = (NodeList) oXpath.evaluate(xpath, node, XPathConstants.NODESET);
        return nodeList;
    }

    /**
     *
     * @param node
     * @param xpath
     * @return
     * @throws XPathExpressionException
     */
    public Node getNode(Node node, String xpath) throws XPathExpressionException {
        Node nodeRet = (Node) oXpath.evaluate(xpath, node, XPathConstants.NODE);
        return nodeRet;
    }

    /**
     *
     * @param document
     * @param filePath
     */
    public void saveXml(Document document,String filePath){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            DOMSource domSource = new DOMSource();
            domSource.setNode(document);
            StreamResult streamResult = new StreamResult();
            streamResult.setOutputStream(new FileOutputStream(filePath));
            transformer.transform(domSource,streamResult);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
}
