import java.util.Scanner;
import java.util.ArrayList;
public class ReverseEven {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int len = 1_000_000;
		String[][] arr = new String[len][];
		int t = 0;

		while (sc.hasNextLine()) {
			arr[t] = sc.nextLine().split(" ");
			t++;
		}
		for (int i = t - 1; i >= 0; i--) {
			for (int j = arr[i].length - 1; j >= 0; j--) {
				if (arr[i][j].length() != 0) {
					if (Integer.parseInt(arr[i][j]) % 2 == 0) {
						System.out.print(arr[i][j] + " ");	
					}
				} else {
					System.out.print(arr[i][j] + " ");
				}
			}
			System.out.println();
		}
	}
}