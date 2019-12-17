import java.lang.*;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.*;
import java.util.Arrays;
import java.util.Stack;

public class K {

	public static void main(String[] args) {

		try {
			File file = new File("input.txt");
			Scanner in = new Scanner(file);

			try {
				int n, m;
				n = in.nextInt();
				m = in.nextInt();
				int[][] mat = new int[n][m];
				for (int i = 0; i < n; i++) {
					String str = in.nextLine();
					for (int j = 0; j < m; j++) {
						if (str.charAt(j) == '0') {
							mat[i][j] = 0;
						} else {
							// int b = str.charAt(j) - 'A' + 1;
							mat[i][j] = 1;
						}
					}
				}
				int[] d = new int[m];
				Arrays.fill(d, -1);
				// for (int j = 0; j < m; j++) {
				// 	System.out.print(d[j] + " ");
				// }
				int[] d1 = new int[m];
				int[] d2 = new int[m];
				int square = 0;
				Stack<Integer> stack = new Stack<Integer>();
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						if (mat[i][j] != 0) {
							d[j] = i;
						}
					}
					// System.out.println("\n\nd");
					// for (int j = 0; j < m; j++) {
					// 	System.out.print(d[j] + " ");
					// }
					while (!stack.empty()) {
						stack.pop();
					}
					for (int j = 0; j < m; j++) {
						while (!stack.empty() && d[stack.peek()] <= d[j]) {
							stack.pop();
						}
						if (stack.empty()) {
							d1[j] = -1;
						} else {
							d1[j] = stack.peek();
						}
						stack.push(j);
					}
					// System.out.println("\nd1");
					// for (int j = 0; j < m; j++) {
					// 	System.out.print(d1[j] + " ");
					// }
					while (!stack.empty()) {
						stack.pop();
					}
					for (int j = m - 1; j > -1; j--) {
						while (!stack.empty() && d[stack.peek()] <= d[j]) {
							stack.pop();
						}
						if (stack.empty()) {
							d2[j] = m;
						} else {
							d2[j] = stack.peek();
						}
						stack.push(j);
					}
					// System.out.println("\nd2");
					// for (int j = 0; j < m; j++) {
					// 	System.out.print(d2[j] + " ");
					// }
					for (int j = 0; j < m; j++) {
						square = Math.max(square, (i - d[j]) * (d2[j] - d1[j] - 1));
					}
				}
				System.out.println(square);
				// for (int i = 0; i < n; i++) {
				// 	for (int j = 0; j < m; j++) {
				// 		System.out.print(mat[i][j]);
				// 	}
				// 	System.out.println();
				// }
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