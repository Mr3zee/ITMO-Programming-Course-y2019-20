package search;

public class SearchEngine {

    public int searchIter(int value, int ... arr) {
        int l = -1, r = arr.length;
        while (r - l > 1) {
            int m = (r - l) / 2 + l;
            if (arr[m] <= value) {
                r = m;
            } else {
                l = m;
            }
        }
        return r;
    }

    public int searchRecur(int l, int r, int value, int ... arr) {
        if (r - l <= 1) {
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
