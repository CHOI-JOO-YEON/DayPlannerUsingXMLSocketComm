package client.View;


import org.xml.sax.InputSource;

import java.io.*;

public class InputView {
    InputStream inputStream;
    BufferedReader bufferedReader;

    public InputView(InputStream inputStream) {
        this.inputStream = inputStream;
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }
    public String getServerMessageByInputStream() throws IOException {
        String message = bufferedReader.readLine();
        if (message == null) {
            return "";
        }
        return message;
    }

    public InputSource getServerSourceByInputStream() throws IOException {
        int bufferSize=0;
        while (bufferSize==0) {
            bufferSize = getBufferSize();
        }
        return getServerSource(bufferSize);
    }

    private int getBufferSize() throws IOException {
        return inputStream.available();
    }
    private InputSource getServerSource(int bufferSize) throws IOException {
        byte buf[] = new byte[bufferSize];
        inputStream.read(buf);
        return new InputSource(new ByteArrayInputStream(buf));
    }
}
