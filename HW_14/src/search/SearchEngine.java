package search;

public class SearchEngine {

    // arr.length == n, arr[-1] == +inf, arr[n] == -inf
    // F(l, r) = (for each -1 <= k <= l : arr[k] > value && for each r <= k <= n : arr[k] <= value)
    // A = (for each -1 <= p < q <= n : arr[p] >= arr[q])
    // Pre: A
    public int searchIter(int value, int ... arr) {
        int l = -1, r = arr.length;
        // l < i <= r && F(-1, n)
        while (r - l != 1) {
            // l < i <= r && r - l > 1 && F(l, r)
            int m = (r - l) / 2 + l;
            // m = (l + r) / 2 && r - l > 1 && -> l < m < r
            // l < i <= r && r - l > 1 && l < m < r && F(l, r)
            if (arr[m] <= value) {
                // arr[m] <= value && A -> for each m <= k <= r : arr[k] <= value -> l < i <= m && F(l, m)
                // l < i <= m && r - l > 1 && F(l, m)
                r = m;
                // l < i <= r && F(l, r)
            } else {
                // arr[m] > value && A -> for each l <= k <= m : arr[k] > value -> m < i <= r && F(m, r)
                // m < i <= r && r - l > 1 && F(m, r)
                l = m;
                // l < i <= r && F(l, r)
            }
            // l < i <= r && F(l, r)
        }
        // (l < i <= r && r - l == 1) -> r == i
        // (r - l == 1 && F(l, r)) -> (i -> min)
        // F(l, r) -> arr[i] <= value
        // arr[i] <= value && (i -> min)
        return r;
    }
    // Post: arr[i] <= value && (i -> min)


    // arr.length == n, arr[-1] == +inf, arr[n] == -inf
    // F(l, r) = (for each -1 <= k <= l : arr[k] > value && for each r <= k <= n : arr[k] <= value)
    // A = (for each -1 <= p < q <= n : arr[p] >= arr[q])
    // Pre: l < i <= r && F(l, r) && A
    public int searchRecur(int l, int r, int value, int ... arr) {
        // l < i <= r && F(l, r)
        if (r - l == 1) {
            // (l < i <= r && r - l == 1) -> r == i
            // (F(l, r) && r - l == 1) -> (i -> min)
            // F(l, r) -> arr[i] <= value
            // arr[i] <= value && (i -> min) && F(l, r)
            return r;
        }
        // l < i <= r && r - l > 1 && F(l, r)
        int m = (r - l) / 2 + l;
        // (m == (l + r) / 2 && r - l > 1) -> l < m < r
        // l < i <= r && r - l > 1 && l < m < r && F(l, r)
        if (arr[m] <= value) {
            // arr[m] <= value && A -> for each m <= k <= r : arr[k] <= value -> l < i <= m && F(l, m)
            // l < i <= m && r - l > 1 && F(l, m)
            r = m;
            // l < i <= r && arr[r] <= value && F(l, r)
        } else {
            // arr[m] > value && Pre -> for each l <= k <= m : arr[k] > value -> m < i <= r && F(m, r)
            // m < i <= r && r - l > 1 && F(m, r)
            l = m;
            // l < i <= r && F(l, r)
        }
        // l < i <= r && F(l, r) && A
        return searchRecur(l, r, value, arr);
        // arr[i] <= value && (i -> min)
    }
    // Post: arr[i] <= value && i -> min


    // arr.length == n, arr[-1] == +inf, arr[n] == -inf
    // Pre: for each -1 <= p < q <= n : arr[p] >= arr[q]
    public int searchRecur(int value, int ... arr) {
        // l = -1 && r == n
        // l < i <= r
        return searchRecur(-1, arr.length, value, arr);
        // arr[i] <= value && i -> min
    }
    // Post: arr[i] <= value && i -> min
}
