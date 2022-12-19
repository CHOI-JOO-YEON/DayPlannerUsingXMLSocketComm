package client.View;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientInputView {
    BufferedReader bufferedReader;

    public ClientInputView(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public String getStringByUserInput() throws IOException {
        String userInput = bufferedReader.readLine();
        if (userInput.equals("")) {
            throw new IllegalArgumentException("잘못된 입력");
        }
        return userInput;
    }

}
