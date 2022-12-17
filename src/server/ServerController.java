package server;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import server.View.*;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

public class ServerController {
    private static Socket socket;
    private static InputView inputView;
    private static boolean checkLogin;
    private static User user;
    private static FileOutputView userFileOutputView;
    private static OutputStreamView outputStreamView;
    private static boolean connection;
    private static NewUser newUser;
    private static PrintStreamView printStreamView;
    private static boolean sendStartMethodXML;
    private static boolean sendUserMethodXML;

    public ServerController(Socket socket, String filePath) throws IOException {
        checkLogin = false;
        sendStartMethodXML = false;
        sendUserMethodXML = false;
        this.socket = socket;
        user = new User(new File(filePath));
        userFileOutputView = new FileOutputView(user.getDocument());
        connection = true;
        inputView = new InputView(socket.getInputStream());
        outputStreamView = new OutputStreamView(socket.getOutputStream());
        printStreamView = new PrintStreamView(new PrintWriter(new OutputStreamWriter(outputStreamView.outputStream)));
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
    }

    private void exit() throws IOException {
        socket.close();
        connection = false;
        checkLogin = false;
        sendStartMethodXML = false;
        sendUserMethodXML = false;
    }

    private void userPageMethod(String userOrder) throws IOException, TransformerException, SAXException {
        if (userOrder.equals("updateIntro")) {
            updateIntro();
            return;
        } else if (userOrder.equals("userList")) {
            sendUserList();
            return;
        } else if (userOrder.equals("exit")) {
            exit();
            return;
        }
    }

    private void sendUserList() throws TransformerException {
        Document sendDocument = user.getUserListWithoutPassword();
        outputStreamView.sendXML(sendDocument);
    }

    private void updateIntro() throws TransformerException, IOException, SAXException {
        Optional<Document> document = user.returnIntroduceDocument(newUser.id);
        if (!document.isEmpty()) {
            outputStreamView.sendXML(document.get());
            String intro = user.getIntroByInputSource(inputView.getUserSourceByInputStream());
            userFileOutputView.updateUserXML(user.updateIntro(newUser.id, intro));
            return;
        }

    }

    private void login() throws IOException, SAXException {
        ServerOutputView.tryLogin();
        InputSource inputSource = inputView.getUserSourceByInputStream();
        newUser = user.convertInputSourceToNewUser(inputSource);
        if (user.isLogin(newUser.id, newUser.password)) {
            checkLogin = true;
            printStreamView.printSuccessMessage();
            return;
        }
        printStreamView.printFailMessage();
    }

    private void join() throws IOException, SAXException {
        ServerOutputView.tryJoin();
        InputSource inputSource = inputView.getUserSourceByInputStream();
        NewUser newUser = user.convertInputSourceToNewUser(inputSource);
        Optional<Document> document = user.getNewXML(newUser);
        if (!document.isEmpty()) {
            userFileOutputView.updateUserXML(document.get());
            printStreamView.printSuccessMessage();
            return;
        }
        printStreamView.printFailMessage();


    }

    void serverControl() throws IOException, SAXException, TransformerException {
        ServerOutputView.clientConnectMessage(socket.getInetAddress());
        while (connection) {
            printServerMessage();
            checkUserOrder();
        }
    }

    private void checkUserOrder() throws IOException, SAXException, TransformerException {
        String userOrder = inputView.getUserOrderByInputStream();
        if (!checkLogin) {
            startPageMethod(userOrder);
        } else if (checkLogin) {
            userPageMethod(userOrder);
        }
    }

    private void printServerMessage() throws IOException, SAXException, TransformerException {
        if (!checkLogin && !sendStartMethodXML) {
            outputStreamView.sendXML(Orders.getStartMethodXML());
            sendStartMethodXML = true;
        } else if (checkLogin && !sendUserMethodXML) {
            outputStreamView.sendXML(Orders.getUserMethodXML());
            sendUserMethodXML = true;
        }

    }
}
