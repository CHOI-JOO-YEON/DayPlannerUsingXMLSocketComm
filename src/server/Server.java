package server;

import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    private String filePath = "source/user.xml";
    private ServerSocket socket;

    public static void main(String[] args) {
        Server server = new Server();
        server.runServer();
    }

    public void runServer() {
        try {
            socket = new ServerSocket(10020, 100);
            serverRun();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private void serverRun() throws IOException, SAXException, TransformerException {
        while (true) {
            try {
                Socket clientSocket = socket.accept();
                ServerController controller = new ServerController(clientSocket, filePath);
                controller.serverControl();
            } catch (SocketException socketException) {

            }

        }
    }
}
