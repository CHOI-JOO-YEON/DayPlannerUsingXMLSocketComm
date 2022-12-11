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

    public User() {

        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }


    public boolean isLogin(String id, String password) {
        try {
            Document document = builder.parse(new File("source/user.xml"));
            NodeList nodeList = document.getDocumentElement().getElementsByTagName("user");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                String userId = node.getChildNodes().item(1).getFirstChild().getNodeValue();
                if (id.equals(userId)) {
                    String userPassword = node.getChildNodes().item(3).getFirstChild().getNodeValue();
                    if (userPassword.equals(password)) {
                        return true;
                    }
                    return false;
                }
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }
}
