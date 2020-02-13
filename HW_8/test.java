import java.io.*;
import java.util.*;
import java.lang.*;


public class test {

    public static void main(String[] args) throws IOException {
        int n;
        Scanner scanner = new Scanner(System.in);

        n = scanner.nextInt();
        String string;
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
           string = scanner.nextLine();
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Character.getNumericValue(string.charAt(j));
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    for (int k = j; k < n; k++) {
                        matrix[i][k] = (matrix[i][k] - matrix[j][k]);
                        if (matrix[i][k] < 0) matrix[i][k] += 10;
                    }
                }
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        scanner.close();
    }
}