import java.lang.*;
import java.io.*;
import java.util.*;

public class test {
	public static void main(String[] args) {
		try {
			String str = null;
			InputStream stream = System.in;
			String line = "12 t57";
			File file = new File("input.txt");
			Scanner in = new Scanner(file);
			try{
				//in.setSkipTokens('a');
				// System.out.println(in.hasNextInt());
				System.out.println(in.hasNext());
				System.out.println(in.hasNextIntInLine());
				System.out.println(in.nextInt());
				System.out.println(in.hasNextIntInLine());
				System.out.println(in.nextInt());
				System.out.println(in.hasNext());
				System.out.println(in.hasNextIntInLine());
				System.out.println(in.nextInt());
				// System.out.println(in.hasNextIntInLine());
				// System.out.println(in.hasNextIntInLine());
				System.out.println(in.hasNextIntInLine());
				System.out.println(in.nextInt());
				System.out.println(in.hasNextIntInLine());
				System.out.println(in.nextInt());
				System.out.println(in.hasNextIntInLine());
				System.out.println(in.nextInt());
				System.out.println(in.hasNextIntInLine());
				int b = '\n';
				// System.out.println(b);
			} finally {
				in.close();
			}
		} catch (IOException e) {
			System.out.println("Something went wrong: " + e.getMessage());
		}
		// try {
		// 	BufferedReader in = new BufferedReader(
		// 		new InputStreamReader(
		// 			new FileInputStream(new File("input.txt")),
		// 			"utf-8"
		// 		)
		// 	);
		// 	String str = in.readLine();
		// 	System.out.println(str);
		// } catch (IOException e) {
		// 	System.out.println("Input file read error: " + e.getMessage());
		// }
	}
}