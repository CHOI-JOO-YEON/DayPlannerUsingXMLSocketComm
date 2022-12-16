package server;

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
                System.out.println(clientSocket.getInetAddress()+"에서 접속했습니다.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
