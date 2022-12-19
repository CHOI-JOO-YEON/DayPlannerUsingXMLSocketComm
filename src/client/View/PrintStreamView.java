package client.View;

import java.io.PrintWriter;

public class PrintStreamView {

    private static PrintWriter printWriter;
    public PrintStreamView(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }


    public void printOrderMessage(String order) {
        printWriter.println(order);
        printWriter.flush();
    }

    public void printMessage(String message) {
        printWriter.println(message);
        printWriter.flush();
    }

}

