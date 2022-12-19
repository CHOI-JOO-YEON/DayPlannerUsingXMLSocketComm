package client;

import client.View.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.Socket;

public class ClientController {
    private static Socket socket;
    private static OutputStream outputStream;
    private static InputStreamReader systemInputStreamReader = new InputStreamReader(System.in);
    private static BufferedReader bufferedReader = new BufferedReader(systemInputStreamReader);
    private static ClientInputView clientInputView = new ClientInputView(bufferedReader);
    private static ClientService clientService;
    private static OutputStreamView outputStreamView;
    private static PrintStreamView printStreamView;
    private static InputStream inputStream;
    private static FileHandler fileHandler;
    private static InputView inputView;
    private static Orders orders;
    private boolean boolStartOrders = false;
    private boolean boolUserOrders = false;
    private boolean loginStatus = false;
    private boolean boolEnd = false;


    public ClientController(Socket socket) throws IOException, ParserConfigurationException {
        this.socket = socket;
        printStreamView = new PrintStreamView(new PrintWriter(socket.getOutputStream()));
        outputStreamView = new OutputStreamView(socket.getOutputStream());
        clientService = new ClientService();


    }

    public void run() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        outputStream = socket.getOutputStream();
        inputStream = socket.getInputStream();
        ClientOutputView.startMessage();
        inputView = new InputView(inputStream);
        fileHandler = new FileHandler();
        orders = new Orders();
        while (!boolEnd) {
            if (!loginStatus) {
                loginMenu();
            } else if (loginStatus) {
                userMenu();
            }
        }
    }

    private void userMenu() throws IOException, SAXException, TransformerException {
        if (!boolUserOrders) {
            setOrdersUser();
        }
        while (!boolEnd) {
            ClientOutputView.printOrders(orders.userOrders);
            String userInput = bufferedReader.readLine();
            String order = orders.getUserOrderByUserInput(userInput);

            if (order.equals("error")) {
                ClientOutputView.printErrorUserInput();
                continue;
            }

            userMethod(order);
        }

    }

    private void userMethod(String order) throws IOException, TransformerException, SAXException {
        System.out.println(order);
        printStreamView.printMessage(order);
        if (order.equals("updateIntro")) {
            updateIntro();
        } else if (order.equals("userList")) {
            userList();
        } else if (order.equals("exit")) {
            socket.close();
            boolEnd = true;

        }
    }

    private void userList() throws IOException, SAXException {
        InputSource inputSource = inputView.getServerSourceByInputStream();
        Document userList = clientService.createIntroDocumentByInputSource(inputSource);
        NodeList nodeList = userList.getDocumentElement().getElementsByTagName("user");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String id = getIdByNode(node);
            String intro = getIntroduceByNode(node);
            System.out.println((i + 1) + ". " + id + ": " + intro);

        }
    }

    private void updateIntro() throws IOException, SAXException, TransformerException {
        InputSource inputSource = inputView.getServerSourceByInputStream();
        Document userRecentIntro = clientService.createIntroDocumentByInputSource(inputSource);

        String recentIntro = String.valueOf(userRecentIntro.getElementsByTagName("intro").item(0).getFirstChild().getNodeValue());
        System.out.println("현재 자기소개 " + recentIntro);
        System.out.println("바꿀 자기소개를 입력하세요");
        String result = bufferedReader.readLine();
        System.out.println("이걸로 괜찮으시겠습니까?: " + result + "(y/n)");
        String answer = bufferedReader.readLine();
        if (answer.equals("y")) {
            outputStreamView.sendXML(clientService.createIntroDocumentByString(result));
        }


    }

    private void setOrdersUser() throws IOException, SAXException {
        Document document = fileHandler.convertInputSourceToDocument(inputView.getServerSourceByInputStream());
        orders.setUserOrders(document);
        boolStartOrders = true;
    }

    private void loginMenu() throws IOException, SAXException, TransformerException {
        if (!boolStartOrders) {
            setOrdersStart();
        }
        while (!loginStatus && !boolEnd) {
            ClientOutputView.printOrders(orders.startOrders);
            String userInput = bufferedReader.readLine();
            String order = orders.getStartOrderByUserInput(userInput);

            if (order.equals("error")) {
                ClientOutputView.printErrorUserInput();
                continue;
            }
            loginMethod(order);
        }

    }

    private void loginMethod(String order) throws IOException, TransformerException {
        printStreamView.printMessage(order);
        if (order.equals("login")) {
            login();
        } else if (order.equals("join")) {
            join();
        } else if (order.equals("exit")) {
            socket.close();
            boolEnd = true;
        }
    }

    private void join() throws IOException, TransformerException {
        try {
            ClientOutputView.printRequestIDInputMessage();
            String id = clientInputView.getStringByUserInput();
            ClientOutputView.printRequestPasswordInputMessage();
            String password = clientInputView.getStringByUserInput();
            ClientOutputView.printRequestIntroInputMessage();
            String intro = clientInputView.getStringByUserInput();
            Document document = clientService.createJoinXML(id, password, intro);
            outputStreamView.sendXML(document);
            if (inputView.getServerMessageByInputStream().equals("성공")) {
                ClientOutputView.printSuccessMessage();
                return;
            }
            ClientOutputView.printFailMessage();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }




    }

    private void login() throws IOException, TransformerException {
        try {

            ClientOutputView.printRequestIDInputMessage();
            String id = clientInputView.getStringByUserInput();
            ClientOutputView.printRequestPasswordInputMessage();
            String password = clientInputView.getStringByUserInput();

            Document document = clientService.createLoginXML(id, password);
            outputStreamView.sendXML(document);
            if (inputView.getServerMessageByInputStream().equals("성공")) {
                loginStatus = true;
                ClientOutputView.printSuccessMessage();
                return;
            }
            ClientOutputView.printFailMessage();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setOrdersStart() throws IOException, SAXException {
        Document document = fileHandler.convertInputSourceToDocument(inputView.getServerSourceByInputStream());
        orders.setStartOrders(document);
        boolStartOrders = true;
    }

    private String getIdByNode(Node node) {
        return node.getChildNodes().item(0).getFirstChild().getNodeValue();
    }

    private String getIntroduceByNode(Node node) {
        return node.getChildNodes().item(1).getFirstChild().getNodeValue();
    }
}
