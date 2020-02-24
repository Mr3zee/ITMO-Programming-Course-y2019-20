import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import queue.ArrayQueue;
import queue.ArrayQueueADT;
import queue.ArrayQueueModule;

import java.util.*;

public abstract class AbstractTest {

    @Before
    public void setUp() {
        System.out.println("Initialisation of " + Colors.paintRed(testName()) + " test");
        System.out.println(Colors.paintCyan("Start of testing"));
        abstractSetUp();
    }

    @After
    public void tearDown() {
        System.out.println(Colors.paintCyan("Testing complete\n"));
    }

    protected abstract void abstractSetUp();

    public abstract String testName();

    @Test
    public abstract void isEmptyTest();

    @Test
    public abstract void sizeTest();

    @Test
    public abstract void elementTest();

    @Test
    public abstract void enqueueTest();

    @Test
    public abstract void clearTest();

    @Test
    public abstract void dequeueTest();

    @Test
    public abstract void doubleQueueTest();

    protected Object value1() {
        Queue<Set<Deque<Integer>>> queue = new ArrayDeque<>();
        Deque<Integer> deque = new ArrayDeque<>();
        Set<Deque<Integer>> set = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            deque.add(i);
        }
        for (int i = 0; i < 500; i++) {
            set.add(deque);
        }
        for (int i = 0; i < 23; i++) {
            queue.add(set);
        }
        return queue;
    }

    protected Object value2() {
        return new StringBuilder().append('1');
    }

    protected Object value3() {
        ArrayList<Map<Integer, Set<Double>>> arrayList = new ArrayList<>();
        arrayList.add(Map.of(1, Set.of(1.0, 1.1)));
        return arrayList;
    }

    protected String isEmptyMessage(boolean expected, String queue) {
        return "Queue " + queue + " is " + (expected ? "" : "not ") + "empty";
    }

    protected String sizeMessage(int expected, String queue) {
        return "Queue " + queue + " has size of " + Colors.paintPurple(String.valueOf(expected));
    }

    protected String enqueueMessage(Object element, String queue) {
        return "Queue " + queue + " has new last element - " + Colors.paintPurple(element.toString());
    }

    protected String elementMessage(Object expected, String queue) {
        return "Queue " + queue + " has " + Colors.paintPurple(expected.toString()) + " as first element";
    }

    protected String clearMessage() {
        return "Queue cleared";
    }

    protected String dequeueMessage(Object expected, String queue) {
        return "Object " + Colors.paintPurple(expected.toString()) + " has been removed from queue " + queue;
    }

    protected String messageColor(String message) {
        return Colors.paintPurple(message);
    }
}
