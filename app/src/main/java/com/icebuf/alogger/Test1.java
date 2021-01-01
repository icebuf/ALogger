package com.icebuf.alogger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 *
 * <p>
 * Test1
 * </p>
 * Description:
 * Test1
 *
 * @author tangjie
 * @version 1.0 on 2020/12/18.
 */
//@ALogTag("app/Test1")
public class Test1 {

//    public static void log() {
//        ALog.d(Test2.class.getName());
//    }

    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream(
                "libalog-compiler/build/poms/pom-default.xml");

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(inputStream);
        NodeList nodeList = document.getElementsByTagName("dependencies");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            if(item instanceof Element) {
                Element element = (Element) item;
//                document.createElement()
                handleDependencyElement(element);
            }
        }
        inputStream.close();
    }

    private static void handleDependencyElement(Element element) {
        NodeList nodeList = element.getElementsByTagName("version");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            if(item instanceof Element) {
                Element e = (Element) item;
                handleVersionElement(e);
            }
        }
    }

    private static void handleVersionElement(Element element) {
        String version = element.getTextContent();
        if (version == null || version.isEmpty() || "unspecified".equals(version)) {
            element.getParentNode().removeChild(element);
        }
    }
}
