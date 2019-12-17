import java.util.Scanner;
import java.util.ArrayList;
public class Reverse {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int len = 1_000_000;
		String[][] arr = new String[len][];
		int t = 0;

		while (sc.hasNextLine()) {
			String str = sc.nextLine();
			arr[t] = str.split(" ");
			t++;
		}
		for (int i = t - 1; i >= 0; i--) {
			for (int j = arr[i].length - 1; j >= 0; j--) {
				System.out.print(arr[i][j]);
				if (j != 0) {
					System.out.print(' ');
				}
			}
			System.out.println();
		}
	}
}