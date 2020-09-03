import java.lang.*;
import java.util.*;
import java.io.*;
import java.util.NoSuchElementException;

public class WordStatLineIndex {
	public static void main(String[] args) {
		NavigableMap<String, List<MyPair>> map = new TreeMap<String, List<MyPair>>();
		int numLines = 0;
		try {
			Scanner in = new Scanner(new File(args[0]));
			try {
				while (in.hasNext()) {
					int numWords = 0;
					numLines++;
					int i = 0;
					while (in.hasNextInLine()) {
						String word = in.next().toLowerCase();
						List<MyPair> temp = map.get(word);
						if (temp == null) {
							temp = new ArrayList<MyPair>();
							map.put(word, temp);
						}
						temp.add(new MyPair(numLines, ++numWords));
					}
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			System.err.print("Problem with input file/stream: " + e.getMessage());
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

