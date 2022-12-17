package server.View;

import java.net.InetAddress;

public class ServerOutputView {
        public static void tryLogin() {
            System.out.println("로그인 시도");
        }
        public static void tryJoin() {
            System.out.println("회원가입 시도");
        }

    public static void clientConnectMessage(InetAddress inetAddress) {
        System.out.println(inetAddress+"에서 접속했습니다.");
    }
}
