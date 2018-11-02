package com.huawei.sprmigrate;

import java.io.File;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

class DomRepHelperTest {

    @Mock
    private Document doc;

    @Mock
    private Node mockNode;

    private DomRepHelper domRepHelper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        domRepHelper = new DomRepHelper();
    }

    @Test
    void parseFile() {
        String absolutePath = new File("D:\\m00416667\\work\\javachasis-demo4mt\\test-spring\\pomb.xml").getPath();
        Document document = domRepHelper.parseFile(absolutePath);
        Assert.assertNotNull(document);

    }

    @Test
    void getNodeValue() {

        Mockito.when(mockNode.getTextContent()).thenReturn("xxx");
        Assert.assertEquals("xxx", domRepHelper.getNodeValue(mockNode));
    }

    @Test
    void delNode() {
        Mockito.when(mockNode.getParentNode()).thenReturn(mockNode);
        domRepHelper.delNode(mockNode);

    }

    @Test
    void saveXml() {
        domRepHelper.saveXml(doc, "");
    }
}