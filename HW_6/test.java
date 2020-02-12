import java.lang.*;
import java.io.*;
import java.util.*;
import java.util.NoSuchElementException;

public class test {
	public static void main(String[] args) {
		try {
			File file = new File("input.txt");
			Scanner in = new Scanner(file);
			while (in.hasNextInLine()) {
				System.out.println(in.next());
			}
		} catch (IOException e) {
			System.err.print("IOException catched: ");
			e.printStackTrace();
		}
	}
}