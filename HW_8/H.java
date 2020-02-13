import java.lang.*;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.*;

public class H {
	
	public static int condition(int[] p, int x, int y) {
		if (x != 0) {
			return p[y] - p[x - 1];
		}
		return p[y];
	}

	public static void main(String[] args) {

		try {
			// File file = new File("input.txt");
			Scanner in = new Scanner(System.in);

			try {
				int n = in.nextInt(); 
				int a1 = Integer.MIN_VALUE;
				int[] a = new int[n];
				int[] p = new int[n];
				for (int i = 0; i < n; i++) {
					a[i] = in.nextInt();
					a1 = Math.max(a1, a[i]);
					if (i == 0) {
						p[i] = a[i];
					} else {
						p[i] = a[i] + p[i - 1];
					}
				}
				Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				int q = in.nextInt();
				for (int i = 0; i < q; i++) {
					int t = in.nextInt();
					if (t >= a1) {
						if (map.containsKey(t)) {
							System.out.println(map.get(t));
						} else {
							int count = 0;
							int j = 0;
							while (j < n) {
								// System.out.println("here1");
								int l = j, r = n;
								while (l < r - 1) {
									int m = (r - l) / 2 + l;
									// System.out.println("l r m condition " + l + r + m + " " + condition(p, l, m));
									if (condition(p, j, m) <= t) {
										// System.out.println(1);
										l = m;
									} else {
										// System.out.println(2);
										r = m;
									}
								}
								count++;
								j = r;
								// System.out.println("j " + j);
							}
							map.put(t, count);
							System.out.println(count);
						} 
					} else {
						System.out.println("Impossible");
					}
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