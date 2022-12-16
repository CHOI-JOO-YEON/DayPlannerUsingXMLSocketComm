package server;

import java.io.PrintWriter;

public class PrintStream {

    private static PrintWriter printWriter;
    public PrintStream(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    void printLoginMessage() {
        printWriter.println("명령어를 입력하시오(login, join, exit)");
        System.out.println("명령어를 입력하시오(login, join, exit)");
        printWriter.flush();
    }
    void printUserMenuMessage() {
        printWriter.println("명령어를 입력하시오(updateIntro, userList, exit)");
        printWriter.flush();
    }
}


