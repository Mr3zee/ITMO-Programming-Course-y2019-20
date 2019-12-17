import java.lang.*;
import java.util.*;
import java.io.*;
import java.util.NoSuchElementException;

public class WordStatLineIndex {
	public static boolean cond(String buffer, int i) {
		return Character.isLetter(buffer.charAt(i)) ||
			Character.getType(buffer.charAt(i)) == Character.DASH_PUNCTUATION ||
			buffer.charAt(i) == '\'';
	}

	public static void main(String[] args) {
		Map<String, List<MyPair>> map = new TreeMap<String, List<MyPair>>();
		int numLines = 0;
		try {
			Scanner in = new Scanner(new File(args[0]));
			try {
				String buffer = in.nextLine();
				while (true) {
					int numWords = 0;
					numLines++;
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
							List<MyPair> temp = map.getOrDefault(word, new ArrayList<MyPair>());
							MyPair tempPair = new MyPair(numLines, ++numWords);
							temp.add(tempPair);
							map.put(word, temp);
						}
					}

					try {
						buffer = in.nextLine();
					} catch (NoSuchElementException e) {
						break;
					}
				}
			} finally {
				in.close();
			}
		} catch (UnsupportedEncodingException e) {
			System.err.print("Encoding error: ");
			e.printStackTrace();
			return;	
		} catch (FileNotFoundException e) {
			System.err.print("No input file found: ");
			e.printStackTrace();
			return;		
		} catch (IOException e) {
			System.err.print("Problem with input file/stream: ");
			e.printStackTrace();
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
				for (Map.Entry<String, List<MyPair>> e : map.entrySet()) {
					List<MyPair> temp2 = e.getValue();
					out.write(e.getKey() + " " + temp2.size());
					for (MyPair tempPair2 : temp2) {
						out.write(" " + tempPair2.getX() + ":" + tempPair2.getY());
					}
					out.newLine();
				}
			} finally {
				out.close();
			}
		} catch (IOException e) {
			System.err.println("Output error: " + e.getMessage());
		}
   	}
}
