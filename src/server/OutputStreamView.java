package server;

import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;

public class OutputStreamView {
    OutputStream outputStream;
    private static TransformerFactory transformerFactory;
    private static Transformer transformer;

    public OutputStreamView(OutputStream outputStream) {
        this.outputStream = outputStream;
        setTransformer();
    }
    private void setTransformer() {
        try {
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
        }catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }
    public void sendXML(Document document) throws TransformerException {
        transformer.transform(new DOMSource(document), new StreamResult(outputStream));
    }

}
