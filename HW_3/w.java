import java.util.Scanner;
import java.util.Arrays;
public class w {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		Scanner sc2 = new Scanner(str);
		int s = 0;
		while(sc2.hasNextInt()) {
			s += sc2.nextInt();
		}
		
		System.out.println(s);
	}
}