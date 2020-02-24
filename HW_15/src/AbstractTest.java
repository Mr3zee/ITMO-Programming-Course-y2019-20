import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
}
