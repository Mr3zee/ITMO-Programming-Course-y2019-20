package search;

public class SearchEngine {

    // Lower bound
    // i - result of the function
    // arr.length == n, arr[-1] == +inf, arr[n] == -inf
    // F(l, r) = (arr[l] > value && arr[r] <= value)
    // A = (for each -1 <= p < q <= n : arr[p] >= arr[q])
    // C = (for each 0 <= j < n : arr[j]' == arr[j])
    //
    // Pre: A
    public int searchIter(int value, int ... arr) {
        int l = -1, r = arr.length;
        // l < i <= r && F(-1, n) && C
        while (r - l != 1) {
            // l < i <= r && r - l > 1 && F(l, r) && C
            int m = (r + l) >>> 1;
            // m = (l + r) / 2 && r - l > 1 && -> l < m < r
            // l < i <= r && r - l > 1 && l < m < r && F(l, r) && C
            if (arr[m] <= value) {
                // arr[m] <= value && A -> for each m <= k <= r : arr[k] <= value -> l < i <= m && F(l, m)
                // l < i <= m && r - l > 1 && F(l, m) && C
                r = m;
                // l < i <= r && F(l, r) && C
            } else {
                // arr[m] > value && A -> for each l <= k <= m : arr[k] > value -> m < i <= r && F(m, r)
                // m < i <= r && r - l > 1 && F(m, r) && C
                l = m;
                // l < i <= r && F(l, r) && C
            }
            // l < i <= r && F(l, r) && C
        }
        // (l < i <= r && r - l == 1) -> r == i
        // (r - l == 1 && F(l, r)) -> (i -> min)
        // F(l, r) -> arr[i] <= value
        // arr[i] <= value && (i -> min) && C
        return r;
    }
    // Post: arr[i] <= value && (i -> min) && C


    // Upper bound
    // i - result of the function
    // arr.length == n, arr[-1] == +inf, arr[n] == -inf
    // F(l, r) = (arr[l] >= value && arr[r] < value)
    // A(l, r) = (for each l <= p < q <= r : arr[p] >= arr[q])
    // C = (for each 0 <= j < n : arr[j]' == arr[j])
    //
    // Pre: l < i <= r && F(l, r) && A(l, r)
    public int searchRecur(int l, int r, int value, int ... arr) {
        // l < i <= r && F(l, r) && C
        if (r - l == 1) {
            // (l < i <= r && r - l == 1) -> r == i
            // (F(l, r) && r - l == 1) -> (i -> min)
            // F(l, r) -> arr[i] < value
            // arr[i] < value && (i -> min) && F(l, r) && C
            return r;
        }
        // l < i <= r && r - l > 1 && F(l, r)
        int m = (r + l) >>> 1;
        // (m == (l + r) / 2 && r - l > 1) -> l < m < r
        // l < i <= r && r - l > 1 && l < m < r && F(l, r) && C
        if (arr[m] < value) {
            // arr[m] < value && A(l, r) -> for each m <= k <= r : arr[k] < value -> l < i <= m && F(l, m)
            // l < i <= m && r - l > 1 && F(l, m) && C
            r = m;
            // l < i <= r && arr[r] < value && F(l, r) && C
        } else {
            // arr[m] >= value && A(l, r) -> for each l <= k <= m : arr[k] >= value -> m < i <= r && F(m, r)
            // m < i <= r && r - l > 1 && F(m, r) && C
            l = m;
            // l < i <= r && F(l, r) && C
        }
        // l < i <= r && F(l, r) && A && C
        return searchRecur(l, r, value, arr);
        // arr[i] < value && (i -> min) && C
    }
    // Post: arr[i] < value && i -> min && C


    // Upper bound
    // i - result of the function
    // arr.length == n, arr[-1] == +inf, arr[n] == -inf
    // F(l, r) = (arr[l] >= value && arr[r] < value)
    // A = (for each -1 <= p < q <= n : arr[p] >= arr[q])
    // C = (for each 0 <= j < n : arr[j]' == arr[j])
    //
    // Pre: A
    public int searchRecur(int value, int ... arr) {
        // l = -1 && r == n
        // l < i <= r && F(l, r) && A
        return searchRecur(-1, arr.length, value, arr);
        // arr[i] < value && (i -> min) && C
    }
    // Post: arr[i] < value && (i -> min) && C
}
