import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import search.SearchEngine;

import java.util.Arrays;

public class Testing {
    SearchEngine engine;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    @Before
    public void setUp() {
        engine = new SearchEngine();
        System.out.println(color("Testing", ANSI_CYAN));
    }

    @After
    public void tearDown() {
        System.out.println(color("Testing complete", ANSI_CYAN));
    }

    @Test
    public void simpleSearch() {
        System.out.println(color("Iterative", ANSI_CYAN));
        validSearchIter(9, 0, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchIter(8, 1, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchIter(7, 2, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchIter(6, 3, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchIter(5, 4, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchIter(4, 5, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchIter(3, 6, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchIter(2, 7, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchIter(1, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchIter(0, 9, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchIter(0, 100, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchIter(10, -100, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        System.out.println(color("Recursive", ANSI_CYAN));
        validSearchRecur(9, 0, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchRecur(8, 1, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchRecur(7, 2, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchRecur(6, 3, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchRecur(5, 4, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchRecur(4, 5, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchRecur(3, 6, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchRecur(2, 7, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchRecur(1, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchRecur(0, 9, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchRecur(0, 100, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        validSearchRecur(10, -100, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
    }

    private void validSearchIter(int expected, int value, int ... arr) {
        Assert.assertEquals(expected, engine.lowerBound(value, arr));
        message(expected, value, arr);
    }

    private void validSearchRecur(int expected, int value, int ... arr) {
        Assert.assertEquals(expected, engine.upperBound(value, arr));
        message(expected, value, arr);
    }

    private void message(int expected, int value, int ... arr) {
        System.out.println("For " + color(Arrays.toString(arr), ANSI_PURPLE) + " and value " + color(value, ANSI_PURPLE) +
                " result is " + color(expected, ANSI_PURPLE) + " - " + color("Passed!", ANSI_YELLOW));
    }

    private String color(String string, String color) {
        return color + string + ANSI_RESET;
    }

    private String color(int i, String color) {
        return color + i + ANSI_RESET;
    }
}
