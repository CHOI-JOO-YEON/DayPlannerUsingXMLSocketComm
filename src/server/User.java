package server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
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


    public User(File userXML) {

        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            this.userXML = userXML;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Optional<Document> getNewXML(NewUser newUser) {
        if(!isNonDuplicateId(newUser.id)){
            return Optional.empty();
        }
        return Optional.ofNullable(getNewUserAddedXML(newUser));
    }

    private Document getNewUserAddedXML(NewUser newUser) {
        updateUserDataByUserXML();
        Node root = document.getDocumentElement();
        Element user = document.createElement("user");
        Element id = document.createElement("id");
        Element password = document.createElement("password");
        Element intro = document.createElement("intro");
        id.appendChild(document.createTextNode(newUser.id));
        password.appendChild(document.createTextNode(newUser.password));
        intro.appendChild(document.createTextNode(newUser.intro));
        user.appendChild(id);
        user.appendChild(password);
        user.appendChild(intro);
        root.appendChild(user);

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
    public NewUser convertInputSourceToNewUser(InputSource inputSource) throws IOException, SAXException {
        Document inputDocument = builder.parse(inputSource);
        String id = inputDocument.getDocumentElement().getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue();
        String password = inputDocument.getDocumentElement().getElementsByTagName("password").item(0).getChildNodes().item(0).getNodeValue();
        String intro = "";
        if (inputDocument.getDocumentElement().getElementsByTagName("intro").getLength() != 0) {
            intro = inputDocument.getDocumentElement().getElementsByTagName("intro").item(0).getChildNodes().item(0).getNodeValue();
        }

        NewUser newUser = new NewUser(id, password, intro);
        return newUser;

    }

    private String getPasswordByNode(Node node) {
        return node.getChildNodes().item(3).getFirstChild().getNodeValue();
    }

    private String getIdByNode(Node node) {
        return node.getChildNodes().item(1).getFirstChild().getNodeValue();
    }

    private String getIntroduceByNode(Node node) {return node.getChildNodes().item(5).getFirstChild().getNodeValue();}
}

