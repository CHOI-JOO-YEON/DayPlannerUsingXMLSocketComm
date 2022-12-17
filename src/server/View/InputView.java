package server.View;


import org.xml.sax.InputSource;

import java.io.*;

public class InputView {
    InputStream inputStream;
    BufferedReader bufferedReader;

    public InputView(InputStream inputStream) {
        this.inputStream = inputStream;
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public String getUserOrderByInputStream() throws IOException {
        return this.bufferedReader.readLine();
    }

    public InputSource getUserSourceByInputStream() throws IOException {
        int bufferSize;
        for(bufferSize = 0; bufferSize == 0; bufferSize = this.getBufferSize()) {
        }

        return this.getUserSource(bufferSize);
    }

    int getBufferSize() throws IOException {
        return this.inputStream.available();
    }

    InputSource getUserSource(int bufferSize) throws IOException {
        byte[] buf = new byte[bufferSize];
        this.inputStream.read(buf);
        return new InputSource(new ByteArrayInputStream(buf));
    }
}
