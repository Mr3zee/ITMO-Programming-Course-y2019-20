import org.junit.Assert;
import org.junit.Test;
import queue.ArrayQueueModule;

import java.lang.reflect.Array;
import java.util.*;

public class ArrayQueueModuleTests extends AbstractTest {

    @Override
    protected void abstractSetUp() {
        ArrayQueueModule.clear();
    }

    @Override
    public String testName() {
        return "ArrayQueueModule";
    }

    @Override
    @Test
    public void isEmptyTest() {
        validIsEmpty(true);
        ArrayQueueModule.enqueue(1);
        validIsEmpty(false);
        ArrayQueueModule.enqueue(1);
        validIsEmpty(false);
        ArrayQueueModule.dequeue();
        validIsEmpty(false);
        ArrayQueueModule.dequeue();
        validIsEmpty(true);
        ArrayQueueModule.enqueue(10);
        validIsEmpty(false);
        ArrayQueueModule.clear();
        validIsEmpty(true);
    }

    @Override
    public void sizeTest() {
        validSize(0);
        ArrayQueueModule.enqueue(1);
        validSize(1);
        for (int i = 0; i < 2456; i++) {
            ArrayQueueModule.enqueue(i);
        }
        validSize(2457);
        ArrayQueueModule.dequeue();
        validSize(2456);
        for (int i = 0; i < 123; i++) {
            ArrayQueueModule.dequeue();
        }
        validSize(2333);
        for (int i = 0; i < 132; i++) {
            ArrayQueueModule.enqueue(i);
        }
        validSize(2465);
        for (int i = 0; i < 168; i++) {
            ArrayQueueModule.enqueue(i);
        }
        validSize(2633);
        ArrayQueueModule.clear();
        validSize(0);
    }

    @Override
    public void elementTest() {
        ArrayQueueModule.enqueue(1);
        validElement(1);
        ArrayQueueModule.dequeue();

        ArrayQueueModule.enqueue("el");
        validElement("el");
        ArrayQueueModule.dequeue();

        ArrayQueueModule.enqueue(true);
        validElement(true);
        ArrayQueueModule.dequeue();

        StringBuilder a = new StringBuilder().append("123");
        ArrayQueueModule.enqueue(a);
        validElement(a);
        ArrayQueueModule.dequeue();

        ArrayList<Map<Integer, Set<Double>>> arrayList = new ArrayList<>();
        arrayList.add(Map.of(1, Set.of(1.0, 1.1)));
        ArrayQueueModule.enqueue(arrayList);
        validElement(arrayList);
    }

    @Override
    public void enqueueTest() {
        validEnqueue(1);
        validEnqueue("sdfg");
        ArrayQueueModule.dequeue();
        validEnqueue(1.0);
        validEnqueue(true);
        validEnqueue(false);
        ArrayQueueModule.clear();
        validEnqueue("hello");
        StringBuilder stringBuilder = new StringBuilder().append('1');
        validEnqueue(stringBuilder);
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
        validEnqueue(queue);
    }

    @Override
    public void clearTest() {
        validClear();
        for (int i = 0; i < 100000; i++) {
            ArrayQueueModule.enqueue(i);
        }
        ArrayQueueModule.clear();
        validClear();
        ArrayQueueModule.enqueue(1);
        ArrayQueueModule.enqueue(2);
        ArrayQueueModule.dequeue();
        validClear();
        ArrayQueueModule.enqueue(2);
        ArrayQueueModule.dequeue();
        validClear();
    }

    @Override
    public void dequeueTest() {
        for (int i = 0; i < 25; i++) {
            ArrayQueueModule.enqueue(i);
        }
        for (int i = 0; i < 25; i++) {
            validDequeue(i);
        }
        ArrayQueueModule.enqueue("123");
        ArrayQueueModule.enqueue("124");
        ArrayQueueModule.enqueue(false);
        ArrayQueueModule.enqueue("125");
        ArrayQueueModule.enqueue(true);
        ArrayQueueModule.enqueue("126");
        ArrayQueueModule.enqueue("hello");
        validDequeue("123");
        validDequeue("124");
        validDequeue(false);
        validDequeue("125");
        validDequeue(true);
        validDequeue("126");
        validDequeue("hello");
    }

    private void validIsEmpty(boolean expected) {
        Assert.assertEquals(expected, ArrayQueueModule.isEmpty());
        System.out.println("Queue " + getQueue() + " is " + (expected ? "" : "not ") + "empty");
    }

    private void validSize(int expected) {
        Assert.assertEquals(expected, ArrayQueueModule.size());
        System.out.println("Queue " + getQueue() + " has size of " + Colors.paintPurple(String.valueOf(expected)));
    }

    private void validEnqueue(Object element) {
        ArrayQueueModule.enqueue(element);
        Assert.assertEquals(element, ArrayQueueModule.lastElement());
        System.out.println("Queue " + getQueue() + " has new last element - " + Colors.paintPurple(element.toString()));
    }

    private void validElement(Object expected) {
        Assert.assertEquals(expected, ArrayQueueModule.element());
        System.out.println("Queue " + getQueue() + " has " + Colors.paintPurple(expected.toString()) + " as first element");
    }

    private void validClear() {
        ArrayQueueModule.clear();
        Assert.assertTrue(ArrayQueueModule.isEmpty());
        System.out.println("Queue cleared");
    }

    private void validDequeue(Object expected) {
        Object removed = ArrayQueueModule.dequeue();
        Assert.assertEquals(expected, removed);
        System.out.println("Object " + Colors.paintPurple(removed.toString()) + " has been removed from queue " + getQueue());
    }

    private String getQueue() {
        return Colors.paintPurple(ArrayQueueModule.makeString());
    }
}
