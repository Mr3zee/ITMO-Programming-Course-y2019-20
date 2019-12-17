package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ScannerChar {
    private BufferedReader in;

    public ScannerChar(File file) throws IOException {
        in = new BufferedReader(
            new InputStreamReader(
                new FileInputStream(file),
                    StandardCharsets.UTF_8
            )
        );
    }

    public int read() throws IOException {
        return in.read();
    }

    public void close() throws IOException {
        in.close();
    }

    public String readSame(char token) throws IOException {
        StringBuilder word = new StringBuilder();
        int current = in.read();
        while (current == token) {
            word.append((char) current);
            current = in.read();
        }
        if (current != -1) {
            word.append((char) current);
        }
        return word.toString();
    }
}

