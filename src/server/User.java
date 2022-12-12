package server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class User {
    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;
    private static Document document;
    private static File userXML;

    public static void main(String[] args) {
        User user = new User(new File("source/user.xml"));
        try {
            Document newUser = builder.parse("source/test/newUser.xml");
            user.getNewXML(newUser);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public User(File userXML) {

        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            this.userXML = userXML;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Optional<Document> getNewXML(Document userDocument) {
        Node node = userDocument.getDocumentElement();
        if(!isNonDuplicateId(getIdByNode(node))){
            return Optional.empty();
        }
        return Optional.ofNullable(getNewUserAddedXML(userDocument));
    }

    private Document getNewUserAddedXML(Document userDocument) {
        updateUserDataByUserXML();
        Element root = userDocument.getDocumentElement();
        document.getDocumentElement().appendChild(root);
        return document;
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
    public Document createIntroduceDocument(String introduce) {
        Document introduceDocument = builder.newDocument();
        Element intro = introduceDocument.createElement("intro");
        intro.appendChild(introduceDocument.createTextNode(introduce));
        introduceDocument.appendChild(intro);
        return introduceDocument;
    }

    public String getIntroByUserXML(String id) {
        updateUserDataByUserXML();
        String result="자기소개를 찾지 못 했습니다.";
        NodeList nodeList = document.getDocumentElement().getElementsByTagName("user");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (getIdByNode(node).equals(id)) {
                result = getIntroduceByNode(node);
                break;
            }
        }
        return result;

    }

    private void updateUserDataByUserXML() {
        try {
            document=builder.parse(userXML);
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

    private String getIntroduceByNode(Node node) {return node.getChildNodes().item(5).getFirstChild().getNodeValue();}
}
