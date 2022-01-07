package cn.original.ide.launch.layout.view.factory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlViewFactory {
    private Document document;
    private Element rootElement;

    public XmlViewFactory(String string) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        //得到 DOM 解析器对象
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse(new ByteArrayInputStream(string.getBytes()));
        Element element = document.getDocumentElement();
        this.rootElement = element;
    }

    public Node instanceNode(String stringName) {
        return document.createElement(stringName);
    }


}
