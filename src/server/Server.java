package server;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket socket;

    public static void main(String[] args) {
        Server server = new Server();
        server.runServer();

    }

    public void runServer() {
        try {
            socket = new ServerSocket(10020, 100);
            while (true) {
                Socket clientSocket = socket.accept();
                Controller controller = new Controller(clientSocket);
                controller.serverControl();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
