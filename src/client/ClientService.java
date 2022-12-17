package client;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ClientService {
    DocumentBuilderFactory factory;
    DocumentBuilder builder;


    public ClientService() throws ParserConfigurationException {
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
    }

    public Document createIntroDocumentByInputSource(InputSource inputSource) throws IOException, SAXException {
        return builder.parse(inputSource);
    }

    public Document createIntroDocumentByString(String userIntro) {
        Document sendDocument = builder.newDocument();
        Element intro = sendDocument.createElement("intro");
        intro.appendChild(sendDocument.createTextNode(userIntro));
        sendDocument.appendChild(intro);
        return sendDocument;
    }


    public Document createLoginXML(String id, String password) {
        Document document = builder.newDocument();
        Element idElement = document.createElement("id");
        Element passwordElement = document.createElement("password");
        idElement.appendChild(document.createTextNode(id));
        passwordElement.appendChild(document.createTextNode(password));
        Element user = document.createElement("user");
        user.appendChild(idElement);
        user.appendChild(passwordElement);
        document.appendChild(user);
        return document;
    }

    public Document createJoinXML(String id, String password, String intro) {
        Document document = builder.newDocument();
        Element idElement = document.createElement("id");
        Element passwordElement = document.createElement("password");
        Element introElement = document.createElement("intro");
        introElement.appendChild(document.createTextNode(intro));
        idElement.appendChild(document.createTextNode(id));
        passwordElement.appendChild(document.createTextNode(password));
        Element user = document.createElement("user");
        user.appendChild(idElement);
        user.appendChild(passwordElement);
        user.appendChild(introElement);
        document.appendChild(user);

        return document;
    }


}
