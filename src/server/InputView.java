package server;

import org.xml.sax.InputSource;

import java.io.*;

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
    InputSource getUserSourceByInputStream() throws IOException {
        int bufferSize=0;
        while (bufferSize==0) {
            bufferSize = getBufferSize();
        }
        return getUserSource(bufferSize);
    }

    int getBufferSize() throws IOException {
        return inputStream.available();
    }
    InputSource getUserSource(int bufferSize) throws IOException {
        byte buf[] = new byte[bufferSize];
        inputStream.read(buf);
        return new InputSource(new ByteArrayInputStream(buf));
    }
}
