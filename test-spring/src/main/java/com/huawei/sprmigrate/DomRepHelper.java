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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
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
        logger.info("begin parse POM content....");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setValidating(false);
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Failed to parse POM file. The failure message is: {}", e.getMessage());
        }
        XPathFactory xPathFactory = XPathFactory.newInstance();
        this.oXpath = xPathFactory.newXPath();
        try {
            return db.parse(filename);
        } catch (SAXException | IOException e) {
            logger.error("Failed to parse POM file. The failure message is: {}", e.getMessage());
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
    public NodeList getNodeList(Node node, String xpath) {
        NodeList nodeList = null;
        try {
            nodeList = (NodeList) oXpath.evaluate(xpath, node, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            logger.error(e.getMessage(), e.getCause());
        }
        return nodeList;
    }

    /**
     *
     * @param node
     * @return
     * @throws Exception
     */
    public Node delNode(Node node) {
        Node node_temp = node.getParentNode();
        logger.debug("temp--->" + node_temp.getNodeName());
        logger.debug("temp--->" + node_temp.getTextContent());
        logger.debug("pa---->" + node_temp.getParentNode().getNodeName());
        logger.debug("pa---->" + node_temp.getParentNode().getTextContent());
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
        logger.info("begin save allfixs to pom file....");
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            DOMSource domSource = new DOMSource();
            domSource.setNode(document);
            StreamResult streamResult = new StreamResult();
            streamResult.setOutputStream(new FileOutputStream(filePath));
            transformer.transform(domSource, streamResult);
        } catch (FileNotFoundException | TransformerException e) {
            logger.error("Failed to save XML file. The failure message is: {}", e.getMessage());
        }

    }
}
