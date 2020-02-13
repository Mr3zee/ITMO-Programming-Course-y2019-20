import java.util.Arrays;
import java.lang.*;
import java.io.*;

public class Reverse {
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);

			int[][] arr = new int[10][];
			int t = 0;
			try {
				while (sc.hasNextLine()) {

					Scanner sc2 = new Scanner(sc.nextLine());
					int t2 = 0;
					int[] temp = new int[10];
					while (sc2.hasNextInt()) {
						int n = sc2.nextInt();
						if (t2 == temp.length) {
							temp = Arrays.copyOf(temp, (3 * temp.length) / 2);
						}
						temp[t2++] = n;
					}
					if (t == arr.length) {
						arr = Arrays.copyOf(arr,  (3 * arr.length) / 2);
					}
					arr[t++] = Arrays.copyOf(temp, t2);
				}
			} finally {
				sc.close();
			}
			for (int i = t - 1; i >= 0; i--) {
				for (int j = arr[i].length - 1; j >= 0; j--) {
					System.out.print(arr[i][j] + " ");
				}
				System.out.println();
			}
		} catch (IOException e) {
			System.out.println("Problem with input file/stream: " + e.getMessage());	
		}
	}
}