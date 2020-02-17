package search;

public class SearchEngine {

    // arr.length == n, arr[-1] == +inf, arr[n] == -inf
    // Invariant: arr[0] >= arr[1] >= ... >= arr[n - 1]
    // Pre: arr[0] >= arr[1] >= ... >= arr[n - 1]
    public int searchIter(int value, int ... arr) {
        int l = -1, r = arr.length;
        // l < i <= r
        while (r - l != 1) {
            // l < i <= r && r - l > 1
            int m = (r - l) / 2 + l;
            // m = (l + r) / 2 && r - l > 1 && -> l < m < r
            // l < i <= r && r - l > 1 && l < m < r
            if (arr[m] <= value) {
                // case 1 : leave at least one arr[k] <= value
                // arr[m] <= value && Pre -> for each m <= k <= r : arr[k] <= value -> l < i <= m
                // l < i <= m && r - l > 1 && && arr[m] <= value
                r = m;
                // l < i <= r && arr[r] <= value
            } else {
                // case 2 : getting rid of all arr[k] > value
                // arr[m] > value && Pre -> for each l <= k <= m : arr[k] > value -> m < i <= r
                // m < i <= r && r - l > 1 && arr[m] > value
                l = m;
                // l < i <= r && arr[l] > value
            }
            // l < i <= r
        }
        // (l < i <= r && r - l == 1) -> r == i
        // l < i <= r && r == i && arr[i] <= value
        // (in case 2 we got rid of all arr[k] > value && in case 1 we left at least one arr[j] <= value) ->
        // -> for each l <= t < i : arr[t] > value -> (l < i <= r && r == i && arr[i] <= value && i -> min)
        // l < i <= r && r == i && arr[i] <= value && i -> min
        return r;
    }
    // Post: arr[i] <= value && i -> min

    public int searchRecur(int l, int r, int value, int ... arr) {
        if (r - l == 1) {
            return r;
        }
        int m = (r - l) / 2 + l;
        if (arr[m] <= value) {
            return searchRecur(l, m, value, arr);
        }
        return searchRecur(m, r, value, arr);
    }

    public int searchRecur(int value, int ... arr) {
        return searchRecur(-1, arr.length, value, arr);
    }
}
