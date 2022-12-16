package server;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Controller {
    private static Socket socket;
    private static InputView inputView;
    private static boolean checkLogin = false;
    private static User user;
    public Controller(Socket socket) {
        this.socket = socket;
        user = new User(new File("source/user.xml"));
    }

    private void startPageMethod(String userOrder) throws IOException, SAXException {
        if (userOrder.equals("login")) {
            login();
        } else if (userOrder.equals("join")) {

        } else if (userOrder.equals("exit")) {

        }
        System.out.println("잘못된 명령어");
    }

    private void login() throws IOException, SAXException {
        System.out.println("login");
        int n = inputView.getBufferSize();
        while (n==0) {
            n = inputView.getBufferSize();
        }

        NewUser newUser = user.convertInputSourceToNewUser(inputView.getUserSourceByInputStream(n));
        if (user.isLogin(newUser.id, newUser.password)) {
            checkLogin = true;
            return;
        }
    }

    void serverControl() throws IOException, SAXException {
        while (true) {
            System.out.println(socket.getInetAddress() + "에서 접속했습니다.");
            inputView = new InputView(socket.getInputStream());
            while (true) {
                if (!checkLogin) {
                    String userOrder = inputView.getUserOrderByInputStream();
                    startPageMethod(userOrder);
                }
            }

        }

    }
}
