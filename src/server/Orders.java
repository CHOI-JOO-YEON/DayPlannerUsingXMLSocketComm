package server;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Orders {
    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private static DocumentBuilder builder;
    private static File startMethodXML = new File("source/startPageOrders.xml");
    private static File userMethodXML = new File("source/userPageOrders.xml");


    static {
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }


    public static Document getStartMethodXML() throws IOException, SAXException {
        return builder.parse(startMethodXML);
    }
    public static Document getUserMethodXML() throws IOException, SAXException {
        return builder.parse(userMethodXML);
    }
}
