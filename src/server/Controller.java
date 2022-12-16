package server;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

public class Controller {
    private static Socket socket;
    private static InputView inputView;
    private static boolean checkLogin = false;
    private static User user;
    private static FileOutputView userFileOutputView;
    private static boolean connection = false;

    public Controller(Socket socket) {
        this.socket = socket;
        user = new User(new File("source/user.xml"));
        userFileOutputView = new FileOutputView(user.getDocument());
        connection = true;
    }

    private void startPageMethod(String userOrder) throws IOException, SAXException {
        if (userOrder.equals("login")) {
            login();
            return;
        } else if (userOrder.equals("join")) {
            join();
            return;
        } else if (userOrder.equals("exit")) {
            socket.close();
            connection = false;
            return;
        }
        System.out.println("잘못된 명령어");
    }

    private void login() throws IOException, SAXException {
        System.out.println("login");
        InputSource inputSource = inputView.getUserSourceByInputStream();
        NewUser newUser = user.convertInputSourceToNewUser(inputSource);
        if (user.isLogin(newUser.id, newUser.password)) {
            checkLogin = true;
            System.out.println("로그인 성공");
            return;
        }
        System.out.println("로그인 실패");
    }

    private void join() throws IOException, SAXException {
        System.out.println("join");
        InputSource inputSource = inputView.getUserSourceByInputStream();
        NewUser newUser = user.convertInputSourceToNewUser(inputSource);
        Optional<Document> document = user.getNewXML(newUser);
        if (!document.isEmpty()) {
            userFileOutputView.updateUserXML(document.get());
            System.out.println("계정 생성 성공");
            return;
        }
        System.out.println("계정 생성 실패");

    }

    void serverControl() throws IOException, SAXException {
        System.out.println(socket.getInetAddress() + "에서 접속했습니다.");
        inputView = new InputView(socket.getInputStream());

        while (true) {
            if (!connection) {
                break;
            }
            if (!checkLogin) {
                String userOrder = inputView.getUserOrderByInputStream();
                startPageMethod(userOrder);
            }
        }


    }
}
