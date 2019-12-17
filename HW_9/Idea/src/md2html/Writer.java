package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Writer {
    private BufferedWriter out;

    public Writer(File file) throws IOException {
        out = new BufferedWriter(
                new  OutputStreamWriter(
                    new FileOutputStream(file),
                        StandardCharsets.UTF_8
                )
        );
    }

    public void write(String line) throws IOException {
        out.write(line);
    }

    public void write(int n) throws IOException {
        out.write(n);
    }

    public void newLine() throws IOException {
        out.newLine();
    }

    public void close() throws IOException {
        out.close();
    }
}
