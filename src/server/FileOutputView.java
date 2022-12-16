package server;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileOutputView {

    private static TransformerFactory transformerFactory;
    private static Transformer transformer;
    private static Document document;
    public FileOutputView(Document document) {
        this.document = document;
        setTransformer();
    }

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

    public void updateUserXML(Document document) {
        try {
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("source/user.xml")));
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
