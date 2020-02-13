import java.util.Scanner;
import java.util.Arrays;

public class ReverseEven {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int[][] arr = new int[10][];
		int t = 0, heh = 0;

		while (sc.hasNextLine()) {

			Scanner sc2 = new Scanner(sc.nextLine());
			int t2 = 0;
			int[] temp = new int[10];
			while (sc2.hasNextInt()) {
				int n = sc2.nextInt();
				//System.out.println(heh++ + ": " + n);
				if (n % 2 == 0) {
					if (t2 == temp.length) {
						temp = Arrays.copyOf(temp, (3 * temp.length) / 2);
					}
					temp[t2++] = n;
				}
			}
			if (t == arr.length) {
				arr = Arrays.copyOf(arr,  (3 * arr.length) / 2);
			}
			arr[t++] = Arrays.copyOf(temp, t2);
		}

		for (int i = t - 1; i >= 0; i--) {
			for (int j = arr[i].length - 1; j >= 0; j--) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
}