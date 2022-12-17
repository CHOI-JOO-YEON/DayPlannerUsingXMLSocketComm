package client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientOutputView {
    public static void startMessage() {
        System.out.println("통신 시작");
    }

    public static void printOrders(ConcurrentHashMap<Integer, String> orders) {
        System.out.print("명령어를 입력하세요 (");
        for (Map.Entry<Integer, String> integerStringEntry : orders.entrySet()) {
            System.out.print(integerStringEntry.getKey()+". "+integerStringEntry.getValue()+" ");
        }
        System.out.println(")");
    }

    public static void printErrorUserInput() {
        System.out.println("잘못된 명령어");
    }

    public static void printRequestIDInputMessage() {
        System.out.println("ID를 입력하세요");
    }


    public static void printRequestIntroInputMessage() {
        System.out.println("자기소개를 입력하세요");
    }


    public static void printRequestPasswordInputMessage() {
        System.out.println("비밀번호를 입력하세요");
    }

}
