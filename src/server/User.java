package server;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class User {
    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;
    private static Document document;

    public User() {

        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }


    public boolean isLogin(String id, String password) {
        updateUserDataByUserXML();
        NodeList nodeList = document.getDocumentElement().getElementsByTagName("user");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (getIdByNode(node).equals(id)) {
                if (getPasswordByNode(node).equals(password)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean isNonDuplicateId(String id) {
        updateUserDataByUserXML();
        NodeList nodeList = document.getDocumentElement().getElementsByTagName("user");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (getIdByNode(node).equals(id)) {
                return false;
            }
        }
        return true;
    }

    private void updateUserDataByUserXML() {
        try {
            document=builder.parse(new File("source/user.xml"));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPasswordByNode(Node node) {
        return node.getChildNodes().item(3).getFirstChild().getNodeValue();
    }

    private String getIdByNode(Node node) {
        return node.getChildNodes().item(1).getFirstChild().getNodeValue();
    }
}
