package search;

public class BinarySearch {
    // Pre: args[0] = value, arr[1] >= arr[2] >= ... >= arr[n - 1]
    public static void main(String[] args) {
        int value = Integer.parseInt(args[0]);
        int[] arr = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            arr[i - 1] = Integer.parseInt(args[i]);
        }
        SearchEngine engine = new SearchEngine();
        System.out.println(engine.searchRecur(value, arr));
    }
    // Post:
}
