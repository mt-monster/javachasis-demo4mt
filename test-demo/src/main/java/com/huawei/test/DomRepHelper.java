package com.huawei.test;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.test
 */
public class DomRepHelper {

    private static final Logger logger = LoggerFactory.getLogger(DomRepHelper.class);

    public XPath oXpath;

    /**
     * 构造xml文档返回对应的document对象
     * @param filename
     * @return
     * @throws Exception
     */
    public Document parseFile(String filename) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setValidating(false);
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        XPathFactory xPathFactory = XPathFactory.newInstance();
        this.oXpath = xPathFactory.newXPath();
        try {
            return db.parse(filename);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
     * @return
     * @throws Exception
     */
    public Node delNode(Node node) throws Exception {
        Node node_temp = node.getParentNode();
        logger.debug(node_temp.getNodeName());
        logger.debug(node_temp.getTextContent());
        return node.getParentNode().removeChild(node);
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
    public void saveXml(Document document, String filePath) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            DOMSource domSource = new DOMSource();
            domSource.setNode(document);
            StreamResult streamResult = new StreamResult();
            streamResult.setOutputStream(new FileOutputStream(filePath));
            transformer.transform(domSource, streamResult);
        } catch (TransformerConfigurationException e) {
            logger.error("Failed to save XML file. The failure message is: {}", e.getMessage());
        } catch (FileNotFoundException e) {
            logger.error("Failed to save XML file. The failure message is: {}", e.getMessage());
        } catch (TransformerException e) {
            logger.error("Failed to save XML file. The failure message is: {}", e.getMessage());
        }

    }
}
