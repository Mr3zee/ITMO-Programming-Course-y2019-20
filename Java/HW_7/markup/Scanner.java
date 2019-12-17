import java.lang.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

public class Scanner {
	String line;
	BufferedReader in;
	int current_pos;
	int temp;
	int next;

	public Scanner(InputStream stream) throws IOException {
		in = new BufferedReader(
			new InputStreamReader(stream)
		);
		myFunction();
	}

	public Scanner(String str) throws IOException {
		in = new BufferedReader(
			new InputStreamReader(
				new ByteArrayInputStream(
					str.getBytes(StandardCharsets.UTF_8)
				)
			)
		);
		myFunction();
	}

	public Scanner(String str, int files) throws IOException {
		in = new BufferedReader(
			new  InputStreamReader(
				new FileInputStream(new File(str)),
				"utf-8"
			)
		);
		myFunction();
	}

	private void myFunction() throws IOException {
		line = in.readLine();
		in.mark(2);
		next = in.read();
		in.reset();
		current_pos = -1;
	}

	private boolean cond() {
		return line != null || next != -1;
	}

	private void ifNull() throws IOException {
		if (current_pos != -1 && current_pos == line.length()) {
			myFunction();
		}
	} 

	private int pos_change() {
		if (current_pos == -1) {
			current_pos = 0;
		}
		return current_pos;
	}

	public int part(int pos) {
		while (pos < line.length() && Character.isWhitespace(line.charAt(pos))) {
			pos++;
		}
		int begin = pos;
		while (pos < line.length() && !Character.isWhitespace(line.charAt(pos))) {
			pos++;
		}
		temp = pos;
		return begin;
	}

	public String nextLine() throws IOException, NoSuchElementException {
		ifNull();
		if (cond()) {
			String str = line.substring(pos_change(), line.length());
			current_pos = line.length();
			return str;
		} else {
			throw new NoSuchElementException("No line found");
		}
	}

	public boolean hasNextLine() throws IOException {
		ifNull();
		return cond();
	}

	public int nextInt() throws IOException, NoSuchElementException {
		ifNull();
		int j = pos_change();
		if (cond()) {
			int b = Integer.parseInt(line.substring(part(j), temp));
			current_pos = temp;
			return b;
		} else {
			throw new NoSuchElementException("No int found");
		}
	}

	public boolean hasNextInt() throws IOException {
		ifNull();
		int j = pos_change();
		if (cond()) {
			try {
				int b = Integer.parseInt(line.substring(part(j), temp));
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public void close() throws IOException {
		in.close();
	}
}