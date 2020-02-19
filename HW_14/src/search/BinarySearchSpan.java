package search;

public class BinarySearchSpan {
    // A = (for each -1 <= p < q <= arr.length : arr[p] >= arr[q])
    // C = for each 0 <= j < n : arr[j]' == arr[j]
    // Pre : A
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
        // First = (arr[i] <= value && (i -> min))
        if (value == Integer.MIN_VALUE) {
            // value = Integer.MIN_VALUE -> for each i <= k < arr.length : arr[k] = value -> span = arr.length - i -> arr.length = i + span
            span = arr.length - i;
            // (arr[i] <= value && (i -> min)) && (for each i <= k < i + span : arr[k] = arr[i]) && C
        } else {
            // value != Integer.MIN_VALUE && C && First
            int next = value - 1;
            // value != Integer.MIN_VALUE -> next < value
            // next < value && C && First
            // C -> A
            // A && next < value && First
            int j = engine.searchIter(next, arr);
            // arr[j] <= next && (j -> min) && next < value && C && First
            // (arr[j] <= next && next < value) -> (arr[j] < value && (j -> min))
            // (arr[j] < value && (j -> min) && First && A) -> for each i <= k < j : arr[k] = arr[i] -> span = j - i -> j = i + span
            span = j - i;
            // (arr[i] <= value && (i -> min)) && (for each i <= k < i + span : arr[k] = arr[i]) && C
        }
        // (arr[i] <= value && (i -> min)) && (for each i <= k < i + span : arr[k] = arr[i]) && C
        System.out.println(i + " " + span);
    }
    // Post: (arr[i] <= value && (i -> min)) && (for each i <= k < i + span : arr[k] = arr[i]) && C
}
