import java.lang.*;
import java.io.*;
import java.util.*;

public class WordStatWords {
	public static boolean cond(String buffer, int i) {
		return Character.isLetter(buffer.charAt(i)) ||
			Character.getType(buffer.charAt(i)) == Character.DASH_PUNCTUATION ||
			buffer.charAt(i) == '\'';
	}

	public static void main(String[] args) {
		Map<String, Integer> map = new TreeMap<String, Integer>();
		try {
			BufferedReader in = new BufferedReader(
				new InputStreamReader(
					new FileInputStream(new File(args[0])),
					"utf-8"
				)
			);
			try {
				String buffer = in.readLine();
				while (buffer != null) {
					int i = 0;
					while (i < buffer.length()) {
						while (i < buffer.length() && !cond(buffer, i)) {
							i++;
						}
						int begin = i;
						while (i < buffer.length() && cond(buffer, i)) {
							i++;
						}
						if (begin != i) {
							String word = buffer.substring(begin, i).toLowerCase();
							map.put(word, map.getOrDefault(word, 0) + 1);
						}
					}
					buffer = in.readLine();
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			System.out.println("Input file read error: " + e.getMessage());
			return;
		}

		try {
			BufferedWriter out = new BufferedWriter(
				new  OutputStreamWriter(
					new FileOutputStream(new File(args[1])),
					"utf-8"
				)
			);
			try {
				for (Map.Entry e : map.entrySet()) {
					out.write(e.getKey() + " " + e.getValue());
					out.newLine();
				}
			} finally {
				out.close();
			}
		} catch (IOException e) {
			System.out.println("Problem with output file: " + e.getMessage());
		}
   	}
}
