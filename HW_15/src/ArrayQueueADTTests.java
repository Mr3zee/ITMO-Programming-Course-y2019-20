import org.junit.Assert;
import org.junit.Test;
import queue.ArrayQueueADT;
import queue.ArrayQueueModule;

import java.util.*;

import static queue.ArrayQueueADT.*;

public class ArrayQueueADTTests extends AbstractTest {
    private ArrayQueueADT queue;

    @Override
    protected void abstractSetUp() {
        queue = new ArrayQueueADT();
    }

    @Override
    public String testName() {
        return "ArrayQueueADT";
    }

    @Override
    public void isEmptyTest() {
        validIsEmpty(true, queue);
        enqueue(queue, 1);
        validIsEmpty(false, queue);
        enqueue(queue, 1);
        validIsEmpty(false, queue);
        dequeue(queue);
        validIsEmpty(false, queue);
        dequeue(queue);
        validIsEmpty(true, queue);
        enqueue(queue, 10);
        validIsEmpty(false, queue);
        clear(queue);
        validIsEmpty(true, queue);
    }

    @Override
    public void sizeTest() {
        validSize(0, queue);
        enqueue(queue, 1);
        validSize(1, queue);
        for (int i = 0; i < 2456; i++) {
            enqueue(queue, i);
        }
        validSize(2457, queue);
        dequeue(queue);
        validSize(2456, queue);
        for (int i = 0; i < 123; i++) {
            dequeue(queue);
        }
        validSize(2333, queue);
        for (int i = 0; i < 132; i++) {
            enqueue(queue, i);
        }
        validSize(2465, queue);
        for (int i = 0; i < 168; i++) {
            enqueue(queue, i);
        }
        validSize(2633, queue);
        clear(queue);
        validSize(0, queue);
    }

    @Override
    public void elementTest() {
        enqueue(queue, 1);
        validElement(1, queue);
        dequeue(queue);

        enqueue(queue, "el");
        validElement("el", queue);
        dequeue(queue);

        enqueue(queue, true);
        validElement(true, queue);
        dequeue(queue);

        StringBuilder a = new StringBuilder().append("123");
        enqueue(queue, a);
        validElement(a, queue);
        dequeue(queue);

        enqueue(queue, value3());
        validElement(value3(), queue);
    }

    @Override
    public void enqueueTest() {
        validEnqueue(1, queue);
        validEnqueue("sdfg", queue);
        dequeue(queue);
        validEnqueue(1.0, queue);
        validEnqueue(true, queue);
        validEnqueue(false, queue);
        clear(queue);
        validEnqueue("hello", queue);
        validEnqueue(value2(), queue);
        validEnqueue(value1(), queue);
    }

    @Override
    public void clearTest() {
        validClear(queue);
        for (int i = 0; i < 100000; i++) {
            enqueue(queue, i);
        }
        validClear(queue);
        enqueue(queue, 1);
        enqueue(queue, 2);
        dequeue(queue);
        validClear(queue);
        enqueue(queue, 2);
        dequeue(queue);
        validClear(queue);
    }

    @Override
    public void dequeueTest() {
        for (int i = 0; i < 25; i++) {
            enqueue(queue, i);
        }
        for (int i = 0; i < 25; i++) {
            validDequeue(i,queue);
        }
        enqueue(queue, "123");
        enqueue(queue, "124");
        enqueue(queue, false);
        enqueue(queue, "125");
        enqueue(queue, true);
        enqueue(queue, "126");
        enqueue(queue, "hello");
        validDequeue("123", queue);
        validDequeue("124", queue);
        validDequeue(false, queue);
        validDequeue("125", queue);
        validDequeue(true, queue);
        validDequeue("126", queue);
        validDequeue("hello", queue);
    }

    @Override
    public void doubleQueueTest() {
        ArrayQueueADT queue2 = new ArrayQueueADT();
        validEnqueue(1, queue);
        validEnqueue(2, queue2);
        validEnqueue(3, queue2);
        validSize(1, queue);
        validSize(2, queue2);
        validElement(1, queue);
        validElement(2, queue2);
        validDequeue(1, queue);
        validDequeue(2, queue2);
        validIsEmpty(true, queue);
        validIsEmpty(false, queue2);
        validClear(queue);
        validClear(queue2);
    }

    private void validIsEmpty(boolean expected, ArrayQueueADT queueADT) {
        Assert.assertEquals(expected, isEmpty(queueADT));
        System.out.println("Queue " + getQueue(queueADT) + " is " + (expected ? "" : "not ") + "empty");
    }

    private void validSize(int expected, ArrayQueueADT queueADT) {
        Assert.assertEquals(expected, size(queueADT));
        System.out.println("Queue " + getQueue(queueADT) + " has size of " + Colors.paintPurple(String.valueOf(expected)));
    }

    private void validEnqueue(Object element, ArrayQueueADT queueADT) {
        enqueue(queueADT, element);
        Assert.assertEquals(element, lastElement(queueADT));
        System.out.println("Queue " + getQueue(queueADT) + " has new last element - " + Colors.paintPurple(element.toString()));
    }

    private void validElement(Object expected, ArrayQueueADT queueADT) {
        Assert.assertEquals(expected, element(queueADT));
        System.out.println("Queue " + getQueue(queueADT) + " has " + Colors.paintPurple(expected.toString()) + " as first element");
    }

    private void validClear(ArrayQueueADT queueADT) {
        clear(queueADT);
        Assert.assertTrue(isEmpty(queueADT));
        System.out.println("Queue cleared");
    }

    private void validDequeue(Object expected, ArrayQueueADT queueADT) {
        Object removed = dequeue(queueADT);
        Assert.assertEquals(expected, removed);
        System.out.println("Object " + Colors.paintPurple(removed.toString()) + " has been removed from queue " + getQueue(queueADT));
    }

    private String getQueue(ArrayQueueADT queueADT) {
        return Colors.paintPurple(makeString(queueADT));
    }
}
