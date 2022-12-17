package client;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class FileHandler {
    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;

    public FileHandler() throws ParserConfigurationException {
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
    }

    public Document convertInputSourceToDocument(InputSource inputSource) throws IOException, SAXException {
        return builder.parse(inputSource);
    }

}
