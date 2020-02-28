package search;

public class SearchEngine {

    // Lower bound
    // i - result of the function
    // arr.length == n, arr[-1] == +inf, arr[n] == -inf
    // F(l, r) = (arr[l] > value && arr[r] <= value)
    // A = (for each -1 <= p < q <= n : arr[p] >= arr[q])
    // C = (for each 0 <= j < n : arr[j]' == arr[j])
    // R(l, r, i) = (arr[i - 1] > value && value >= arr[i] && l < i <= r)
    //
    // Pre: A
    public int lowerBound(int value, int ... arr) {
        int l = -1, r = arr.length;
        // F(-1, n) && C
        while (r - l != 1) {
            // r - l > 1 && F(l, r) && C
            int m = (r + l) >>> 1;
            // m = (l + r) / 2 && r - l > 1 && -> l < m < r
            // r - l > 1 && l < m < r && F(l, r) && C
            if (arr[m] <= value) {
                // arr[m] <= value && A -> for each m <= k <= r : arr[k] <= value -> F(l, m)
                // r - l > 1 && F(l, m) && C
                r = m;
                // F(l, r) && C
            } else {
                // arr[m] > value && A -> for each l <= k <= m : arr[k] > value -> F(m, r)
                // r - l > 1 && F(m, r) && C
                l = m;
                // F(l, r) && C
            }
            // F(l, r) && C
        }
        // (F(l, r) && r - l == 1) -> R(l, r, r) -> (i = r)
        // R(-1, n, i) && C
        return r;
    }
    // Post: R(-1, n, i) && C


    // Upper bound
    // i - result of the function
    // arr.length == n, arr[l - 1] == +inf, arr[r] == -inf
    // F(l, r) = (arr[l] >= value && value > arr[r])
    // A(l, r) = (for each l <= p < q <= r : arr[p] >= arr[q])
    // C = (for each 0 <= j < n : arr[j]' == arr[j])
    // R'(l, r, i) = (arr[i - 1] >= value && value > arr[i] && l < i <= r)
    //
    // Pre: F(l, r) && A(l, r)
    public int upperBound(int l, int r, int value, int ... arr) {
        // F(l, r) && C
        if (r - l == 1) {
            // (F(l, r) && r - l == 1) -> R'(l, r, r) -> i = r
            // R'(l, r, i) && C
            return r;
        }
        // r - l > 1 && F(l, r)
        int m = (r + l) >>> 1;
        // (m == (l + r) / 2 && r - l > 1 && A(l, r) -> l < m < r
        // r - l > 1 && l < m < r && F(l, r) && C
        if (arr[m] < value) {
            // arr[m] < value && A(l, r) -> for each m <= k <= r : arr[k] < value -> F(l, m)
            // r - l > 1 && F(l, m) && C
            r = m;
            // F(l', r') && r' < r && l' == l && C
        } else {
            // arr[m] >= value && A(l, r) -> for each l <= k <= m : arr[k] >= value -> F(m, r)
            // r - l > 1 && F(m, r) && C
            l = m;
            // F(l', r') && l' > l && r' == r && C
        }
        // F(l', r') && A(l, r) && C
        return upperBound(l, r, value, arr);
        // R'(l', r', i) && C
        // l <= l' && r' <= r -> R'(l, r, i)
        // R'(l, r, i) && C
    }
    // Post: R'(l, r, i) && C


    // Upper bound
    // i - result of the function
    // arr.length == n, arr[-1] == +inf, arr[n] == -inf
    // F(l, r) = (arr[l] >= value && arr[r] < value)
    // A = (for each -1 <= p < q <= n : arr[p] >= arr[q])
    // C = (for each 0 <= j < n : arr[j]' == arr[j])
    // R'(l, r, i) = (arr[i - 1] >= value && value > arr[i] && l < i <= r)
    //
    // Pre: A
    public int upperBound(int value, int ... arr) {
        // F(-1, n) && A(-1, n)
        return upperBound(-1, arr.length, value, arr);
        // R'(-1, n, i) && C
    }
    // Post: R'(-1, n, i) && C
}
