import java.lang.*;
import java.io.*;
import java.util.Scanner;

public class A {
	public static void main(String[] args) {
		// try {
			//File file = new File("input.txt");
			Scanner in = new Scanner(System.in);
			// try {
				int a, b, n;
				a = in.nextInt();
				b = in.nextInt();
				n = in.nextInt();
				a = b - a;
				b = n - b;
				int s = Math.round(b / a);
				if (a * s < b) {
					s++;
				}
				System.out.println(2 * s + 1);
		// 	} finally {
		// 		in.close();
		// 	}
		// } catch (IOException e) {
		// 	System.out.println(e.getMessage());
		// } 
	}
}