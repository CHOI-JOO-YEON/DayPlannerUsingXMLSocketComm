package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputView {
    InputStream inputStream;
    BufferedReader bufferedReader;

    public InputView(InputStream inputStream) {
        this.inputStream = inputStream;
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    String getUserOrderByInputStream() throws IOException {
        return bufferedReader.readLine();
    }
}
