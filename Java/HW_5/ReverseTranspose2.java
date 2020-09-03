import java.util.Arrays;
import java.lang.*;
import java.io.*;
import java.util.*;

public class ReverseTranspose { 
	public static void main(String[] args) { 
		try {
			//File file = new File("input.txt");
			Scanner sc = new Scanner(System.in);

			List<List<Integer>> list = new ArrayList<List<Integer>>();
			try {
				while (sc.hasNext()) {
					int words = 0;
					Scanner sc2 = new Scanner(sc.nextLine());

					while (sc2.hasNextInt()) {
						if (words == list.size()) {
							list.add(new ArrayList<Integer>());
						}
						list.get(words).add(sc2.nextInt());
						words++;
					}
				}
			} finally {
				sc.close();
			}
			for(List<Integer> mylist : list) {
				for (int n : mylist) {
					System.out.print(n + " ");
				}
				System.out.println();
			}
		} catch (IOException e) {
			System.err.println("Problem with input file/stream: " + e.getMessage());	
		}
	}
}