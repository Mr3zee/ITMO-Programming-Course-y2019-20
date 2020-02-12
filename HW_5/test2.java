import java.lang.*;
import java.io.*;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class test2 {
	public static void main(String[] args) {
		// try {
		// 	BufferedReader in = new BufferedReader(
		// 		new InputStreamReader(
		// 			System.in
		// 			// new ByteArrayInputStream(
		// 			// 	"123 s".getBytes(StandardCharsets.UTF_8)
		// 			// )
		// 		)
		// 	);
		// 	System.out.println("!" + (char)10 + "!");
		// 	System.out.println(" 11" + (char)13 + "22");
		// 	System.out.println(in.read());
		// 	System.out.println(1);
		// 	System.out.println(in.read());
		// 	System.out.println(2);
		// 	System.out.println(in.read());
		// 	System.out.println(3);
		// 	System.out.println(in.read());
		// 	System.out.println(4);
		// 	in.close();
		// } catch (IOException e) {
		// 	System.out.println(e.getMessage());
		// }
		StringBuilder str = new StringBuilder();
		int z;
		str.append("1");
		// str.insert(0, "123");
		// str.delete(0, str.length());
		// str.append("s");
		// Scanner sc = new Scanner("");
		// System.out.println(sc.hasNextLine());
		System.out.println(Integer.parseInt(str.substring(0, 2)));
	}
}