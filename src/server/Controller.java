package server;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
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
    private static OutputStreamView outputStreamView;
    private static boolean connection = false;
    private static NewUser newUser;

    public Controller(Socket socket) throws IOException {
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
            exit();
            return;
        }
        System.out.println("잘못된 명령어");
    }

    private void exit() throws IOException {
        socket.close();
        connection = false;
        checkLogin = false;
    }

    private void userPageMethod(String userOrder) throws IOException, TransformerException, SAXException {
        if (userOrder.equals("updateIntro")) {
            updateIntro();
            return;
        } else if (userOrder.equals("userList")) {
            returnUserList();
            return;
        } else if (userOrder.equals("exit")) {
            exit();
            return;
        }
        System.out.println("잘못된 명령어");
    }

    private void returnUserList() throws TransformerException {
        Document returnDocument = user.getUserListWithoutPassword();
        outputStreamView.sendXML(returnDocument);

    }

    private void updateIntro() throws TransformerException, IOException, SAXException {
        Optional<Document> document = user.returnIntroduceDocument(newUser.id);
        if (!document.isEmpty()) {
            outputStreamView.sendXML(document.get());
            String intro = user.getIntroByInputSource(inputView.getUserSourceByInputStream());
            userFileOutputView.updateUserXML(user.updateIntro(newUser.id, intro));
            return;
        }
        System.out.println("error");

    }

    private void login() throws IOException, SAXException {
        System.out.println("login");
        InputSource inputSource = inputView.getUserSourceByInputStream();
        newUser = user.convertInputSourceToNewUser(inputSource);
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

    void serverControl() throws IOException, SAXException, TransformerException {
        System.out.println(socket.getInetAddress() + "에서 접속했습니다.");
        inputView = new InputView(socket.getInputStream());
        outputStreamView = new OutputStreamView(socket.getOutputStream());
        while (true) {
            if (!connection) {
                break;
            }
            String userOrder = inputView.getUserOrderByInputStream();
            if (!checkLogin) {
                startPageMethod(userOrder);
            } else if (checkLogin) {
                userPageMethod(userOrder);
            }
        }


    }
}
