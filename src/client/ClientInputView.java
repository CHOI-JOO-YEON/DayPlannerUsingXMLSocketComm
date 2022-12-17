package client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientInputView {
    BufferedReader bufferedReader;

    public ClientInputView(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public String getStringByUserInput() throws IOException {
        return bufferedReader.readLine();
    }

}
