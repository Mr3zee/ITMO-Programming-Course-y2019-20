package search;

public class BinarySearchSpan {
    // args.length = n + 1
    // value = args[0]
    // arr[0; n - 1] = args[1; n]
    // A = (for each 0 <= p < q < n : arr[p] >= arr[q])
    // C = (for each 0 <= j < n : arr[j]' == arr[j])
    //
    // Pre : A && n >= 0
    public static void main(String[] args) {
        int span;
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
        int i = engine.searchIter(value, arr);
        // arr[i] <= value && (i -> min) && C
        // C -> A
        // A
        int j = engine.searchRecur(value, arr);
        // arr[j] < value && (j -> min) && C
        // (arr[i] <= value && (i -> min)) && (arr[j] < value && (j -> min)) -> for each i <= k < j : arr[k] = arr[i] -> span = j - i -> j = i + span
        span = j - i;
        // (arr[i] <= value && (i -> min)) && (for each i <= k < i + span : arr[k] = arr[i]) && C
        System.out.println(i + " " + span);
    }
    // Post: (arr[i] <= value && (i -> min)) && (for each i <= k < i + span : arr[k] = arr[i]) && C
}
