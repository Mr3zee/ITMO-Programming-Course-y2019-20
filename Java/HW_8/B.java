import java.lang.*;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class B {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		for (int i = 0; i < n ; i++) {
			System.out.println((-710) * (25000 - i) + " ");
			//System.out.println(Math.sin((-710) * (25000 - i)));
		}
	}
}