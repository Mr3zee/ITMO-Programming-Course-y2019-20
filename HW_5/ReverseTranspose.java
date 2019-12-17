import java.util.Arrays;
import java.lang.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class ReverseTranspose { 
	public static void main(String[] args) { 
		try {
			Scanner sc = new Scanner(System.in);

			List<int[]> list = new ArrayList<int[]>();
			try {
				while (sc.hasNext()) {
					int words = 0;

					while (sc.hasNextIntInLine()) {
						if (words == list.size()) {
							list.add(new int[10]);
							list.get(words)[0] = 0;
						}
						int[] temp = list.get(words);
						if (temp[0] + 1 == temp.length) {
							temp = Arrays.copyOf(temp,  (3 * temp.length) / 2);
						}
						temp[++temp[0]] = sc.nextInt();
						list.set(words, temp);
						words++;
					}
				}
				for (int i = 0; i < list.size(); i++) {
					list.set(i, Arrays.copyOf(list.get(i), list.get(i)[0] + 1));
				}
			} finally {
				sc.close();
			}
			
			for (int[] arr : list) {
				for (int i = 1; i < arr.length; i++) {
					System.out.print(arr[i] + " ");
				}
				System.out.println();
			}
		} catch (IOException e) {
			System.err.println("Problem with input stream: ");	
			e.printStackTrace();
		}
	}
}