import java.util.Scanner;
import java.util.ArrayList;
public class Reverse {
	public static void main(String[] args) {
		ArrayList<int[]> arr = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			if(sc.nextLine().length() > 0){
				int[] temp= new int[sc.nextLine().split(" ").length];
				for (int i = 0; i < sc.nextLine().split(" ").length ; i++) {
					temp[i] = Integer.parseInt(sc.nextLine().split(" ")[i]);
				}
				arr.add(temp);
			} else {
				arr.add(null);
			}
		}
		for (int i = arr.size() - 1; i >= 0; i--) {
			for (int j = arr.get(i).length - 1; j >= 0; j--) {
				System.out.print(arr.get(i)[j]);
				if (j != 0) {
					System.out.print(' ');
				}
			}
			System.out.println();
		}
	}
}