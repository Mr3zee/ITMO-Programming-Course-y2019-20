import org.junit.Assert;
import queue.ArrayQueueADT;

import static queue.ArrayQueueADT.*;

public class ArrayQueueADTTests extends AbstractTest {
    private ArrayQueueADT queueADT;

    @Override
    protected void abstractSetUp() {
        queueADT = new ArrayQueueADT();
    }

    @Override
    public String testName() {
        return "ArrayQueueADT";
    }

    @Override
    public void isEmptyTest() {
        validIsEmpty(true, queueADT);
        enqueue(queueADT, 1);
        validIsEmpty(false, queueADT);
        enqueue(queueADT, 1);
        validIsEmpty(false, queueADT);
        dequeue(queueADT);
        validIsEmpty(false, queueADT);
        dequeue(queueADT);
        validIsEmpty(true, queueADT);
        enqueue(queueADT, 10);
        validIsEmpty(false, queueADT);
        clear(queueADT);
        validIsEmpty(true, queueADT);
    }

    @Override
    public void sizeTest() {
        validSize(0, queueADT);
        enqueue(queueADT, 1);
        validSize(1, queueADT);
        for (int i = 0; i < 2456; i++) {
            enqueue(queueADT, i);
        }
        validSize(2457, queueADT);
        dequeue(queueADT);
        validSize(2456, queueADT);
        for (int i = 0; i < 123; i++) {
            dequeue(queueADT);
        }
        validSize(2333, queueADT);
        for (int i = 0; i < 132; i++) {
            enqueue(queueADT, i);
        }
        validSize(2465, queueADT);
        for (int i = 0; i < 168; i++) {
            enqueue(queueADT, i);
        }
        validSize(2633, queueADT);
        clear(queueADT);
        validSize(0, queueADT);
    }

    @Override
    public void elementTest() {
        enqueue(queueADT, 1);
        validElement(1, queueADT);
        dequeue(queueADT);

        enqueue(queueADT, "el");
        validElement("el", queueADT);
        dequeue(queueADT);

        enqueue(queueADT, true);
        validElement(true, queueADT);
        dequeue(queueADT);

        StringBuilder a = new StringBuilder().append("123");
        enqueue(queueADT, a);
        validElement(a, queueADT);
        dequeue(queueADT);

        enqueue(queueADT, value3());
        validElement(value3(), queueADT);
    }

    @Override
    public void enqueueTest() {
        validEnqueue(1, queueADT);
        validEnqueue("sdfg", queueADT);
        dequeue(queueADT);
        validEnqueue(1.0, queueADT);
        validEnqueue(true, queueADT);
        validEnqueue(false, queueADT);
        clear(queueADT);
        validEnqueue("hello", queueADT);
        validEnqueue(value2(), queueADT);
        validEnqueue(value1(), queueADT);

        clear(queueADT);
        enqueue(queueADT, 1);
        for (int i = 0; i < 3; i++) {
            enqueue(queueADT, 1);
            dequeue(queueADT);
        }
        for (int i = 0; i < 8; i++) {
            enqueue(queueADT, 2);
        }
        validEnqueue("hello", queueADT);
    }

    @Override
    public void clearTest() {
        validClear(queueADT);
        for (int i = 0; i < 100000; i++) {
            enqueue(queueADT, i);
        }
        validClear(queueADT);
        enqueue(queueADT, 1);
        enqueue(queueADT, 2);
        dequeue(queueADT);
        validClear(queueADT);
        enqueue(queueADT, 2);
        dequeue(queueADT);
        validClear(queueADT);
    }

    @Override
    public void dequeueTest() {
        for (int i = 0; i < 25; i++) {
            enqueue(queueADT, i);
        }
        for (int i = 0; i < 25; i++) {
            validDequeue(i, queueADT);
        }
        enqueue(queueADT, "123");
        enqueue(queueADT, "124");
        enqueue(queueADT, false);
        enqueue(queueADT, "125");
        enqueue(queueADT, true);
        enqueue(queueADT, "126");
        enqueue(queueADT, "hello");
        validDequeue("123", queueADT);
        validDequeue("124", queueADT);
        validDequeue(false, queueADT);
        validDequeue("125", queueADT);
        validDequeue(true, queueADT);
        validDequeue("126", queueADT);
        validDequeue("hello", queueADT);
    }

    @Override
    public void doubleQueueTest() {
        ArrayQueueADT queueADT2 = new ArrayQueueADT();
        validEnqueue(1, queueADT);
        validEnqueue(2, queueADT2);
        validEnqueue(3, queueADT2);
        validSize(1, queueADT);
        validSize(2, queueADT2);
        validElement(1, queueADT);
        validElement(2, queueADT2);
        validDequeue(1, queueADT);
        validDequeue(2, queueADT2);
        validIsEmpty(true, queueADT);
        validIsEmpty(false, queueADT2);
        validClear(queueADT);
        validClear(queueADT2);
    }

    private void validIsEmpty(boolean expected, ArrayQueueADT queueADT) {
        Assert.assertEquals(expected, isEmpty(queueADT));
        System.out.println(isEmptyMessage(expected, getQueue(queueADT)));
    }

    private void validSize(int expected, ArrayQueueADT queueADT) {
        Assert.assertEquals(expected, size(queueADT));
        System.out.println(sizeMessage(expected, getQueue(queueADT)));
    }

    private void validEnqueue(Object element, ArrayQueueADT queueADT) {
        enqueue(queueADT, element);
        Assert.assertEquals(element, lastElement(queueADT));
        System.out.println(elementMessage(element, getQueue(queueADT)));
    }

    private void validElement(Object expected, ArrayQueueADT queueADT) {
        Assert.assertEquals(expected, element(queueADT));
        System.out.println(elementMessage(expected, getQueue(queueADT)));
    }

    private void validClear(ArrayQueueADT queueADT) {
        clear(queueADT);
        Assert.assertTrue(isEmpty(queueADT));
        System.out.println(clearMessage());
    }

    private void validDequeue(Object expected, ArrayQueueADT queueADT) {
        Assert.assertEquals(expected, dequeue(queueADT));
        System.out.println(dequeueMessage(expected, getQueue(queueADT)));
    }

    private String getQueue(ArrayQueueADT queueADT) {
        return messageColor(toStr(queueADT));
    }
}
