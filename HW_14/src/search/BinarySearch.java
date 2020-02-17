package search;

public class BinarySearch {
    // A = (for each -1 <= p < q <= n : arr[p] >= arr[q])
    // C = for each 0 <= j < n : arr[j]' == arr[j]
    // Pre : A
    public static void main(String[] args) {
        int value = Integer.parseInt(args[0]);
        // C
        int[] arr = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            arr[i - 1] = Integer.parseInt(args[i]);
        }
        // C
        SearchEngine engine = new SearchEngine();
        // C -> A
        // A
        int i = engine.searchRecur(value, arr);
        // arr[i] <= value && (i -> min) && C
        System.out.println(i);
    }
    // Post: arr[i] <= value && (i -> min) && C
}
