package server.View;

import java.io.PrintWriter;

public class PrintStreamView {

    private static PrintWriter printWriter;
    public PrintStreamView(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public void printLoginMessage() {
        printWriter.println("명령어를 입력하시오(login, join, exit)");
        printWriter.flush();
    }
    public void printUserMenuMessage() {
        printWriter.println("명령어를 입력하시오(updateIntro, userList, exit)");
        printWriter.flush();
    }
    public void printFailMessage() {
        printWriter.println("실패");
        printWriter.flush();
    }
    public void printSuccessMessage() {
        printWriter.println("성공");
        printWriter.flush();
    }

}


