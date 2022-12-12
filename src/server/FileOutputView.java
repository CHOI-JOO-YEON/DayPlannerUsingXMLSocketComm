package server;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

public class FileOutputView {

    private static TransformerFactory transformerFactory;
    private static Transformer transformer;
    private static Document document;

    private void setTransformer() {
        try {
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            DocumentType documentType = document.getImplementation().createDocumentType("doctype", "", "user.dtd");
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("omit-xml-declaration", "no");
            transformer.setOutputProperty("method", "xml");
            transformer.setOutputProperty("doctype-system", documentType.getSystemId());
        }catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }
}
