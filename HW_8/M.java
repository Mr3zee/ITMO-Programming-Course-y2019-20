import java.lang.*;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.*;

public class M {

	public static void main(String[] args) {

		try {
			File file = new File("input.txt");
			Scanner in = new Scanner(file);

			try {
				int t = in.nextInt();
				for (int x = 0; x < t; x++) {
					int ans = 0;
					int n = in.nextInt();
					Map<Integer, Integer> map = new HashMap<Integer, Integer>();
					int[] v = new int[n];
					for (int i = 0; i < n; i++) {
						v[i] = in.nextInt();
					}
					for (int j = n - 2; j > 0; j--) {
						// System.out.println(v[j + 1]);
						if (map.containsKey(v[j + 1])) {
							map.put(v[j + 1], map.get(v[j + 1]) + 1);
						} else {
							map.put(v[j + 1], 1);
						}
						// System.out.println(map.get(v[j + 1]));
						for (int i = 0; i < j; i++) {
							// System.out.println((2 * v[j] - v[i]));
							if (map.containsKey(2 * v[j] - v[i])) {
								ans += map.get(2 * v[j] - v[i]);
							}
						}
					}
					System.out.println(ans);
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			System.out.println("Problem with input: " + e.getMessage());
		}
	}

	public static class Scanner {
		private String word;
		private BufferedReader in;
		private boolean ableToRead = true;
		private char toSkip = ' ';
		private int current;
		private boolean endOfLine = false;

		public Scanner(InputStream stream) throws IOException {
			in = new BufferedReader(new InputStreamReader(stream, "utf-8"));
			word = nextWord();
		}

		public Scanner(File file) throws IOException {
			in = new BufferedReader(
				new  InputStreamReader(
					new FileInputStream(file),
					"utf-8"
				)
			);
			word = nextWord();
		}

		public String next() throws IOException {
			canRead();
			String temp = word;
			endOfLine = false;
			word = nextWord();
			return temp;
		}

		public boolean hasNext() {
			return ableToRead;
		}

		private void canRead() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
		}

		private void findEndOfLine() {
			if(!endOfLine) {
				endOfLine = current == 10;
			}
		}

		private void resetEndOfLine() {
			if (hasNext()) {
				endOfLine = false;
			}
		}

		public String nextLine() throws IOException {
			canRead();
			StringBuilder newLine = new StringBuilder(word + " ");
			current = in.read();
			setSkipTokens('\n');
			getWord(newLine);
			setSkipTokens(' ');
			endOfLine = false;
			word = nextWord();
			return newLine.toString();
		}

		public int nextInt() throws IOException {
			canRead();
			return Integer.parseInt(next());
		}

		public boolean hasNextInt() throws IOException {
			if (!hasNext()) {
				return false;
			}
			try {
				int temp = Integer.parseInt(word);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		public boolean hasNextIntInLine() throws IOException {
			if (endOfLine || !hasNext()) {
				resetEndOfLine();
				return false;
			}
			try {
				int temp = Integer.parseInt(word);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		private void setSkipTokens(char toSkip) {
			this.toSkip = toSkip;
		}

		private boolean skipTokens(char token) {
			if (toSkip == ' ') {
				return Character.isWhitespace(token);
			}
			return token == toSkip;
		}

		private void goToWord() throws IOException {
			while (current != -1 && skipTokens((char)current)) {
				current = in.read();
				findEndOfLine();
			}
		}

		private void getWord(StringBuilder newWord) throws IOException {
			while (current != -1 && !skipTokens((char)current)) {
				newWord.append((char) current);
				current = in.read();
				findEndOfLine();
			}
		}

		private String nextWord() throws IOException {
			StringBuilder newWord = new StringBuilder();
			current = in.read();
			findEndOfLine();
			goToWord();
			getWord(newWord);
			if (current == -1 && newWord.length() == 0) {
				ableToRead = false;
			}
			return newWord.toString();
		}

		public void close() throws IOException {
			in.close();
		}
	}
}