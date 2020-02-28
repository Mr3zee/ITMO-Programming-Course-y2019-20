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

        for (int i = 0; i < 300; i++) {
            enqueue(queueADT, "hi");
        }
        validElement(a, queueADT);
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

        clear(queueADT);
        enqueue(queueADT, 1);
        for (int i = 0; i < 3; i++) {
            enqueue(queueADT, 1);
            dequeue(queueADT);
        }
        for (int i = 0; i < 1500; i++) {
            validEnqueue(i, queueADT);
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

    @Override
    public void pushTest() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 10; j++) {
                validPush(j + "world", queueADT);
            }
            validPush("hello" + i, queueADT);
            for (int j = 0; j < 5; j++) {
                remove(queueADT);
            }
        }
    }

    @Override
    public void peekTest() {
        for (int i = 0; i < 1500; i++) {
            enqueue(queueADT, 22);
            validPeek(22, queueADT);
        }
        dequeue(queueADT);
        enqueue(queueADT, "hello");
        validPeek("hello", queueADT);
    }

    @Override
    public void removeTest() {
        for (int i = 0; i < 2000; i++) {
            push(queueADT, i);
        }
        for (int i = 0; i < 2000; i++) {
            validRemove(i, queueADT);
        }
        push(queueADT, "hello");
        push(queueADT, "world");
        validRemove("hello", queueADT);
        validRemove("world", queueADT);
    }

    @Override
    public void toArrayTest() {

    }

    @Override
    public void toStrTest() {

    }

    protected void validIsEmpty(boolean expected, ArrayQueueADT queueADT) {
        validIsEmpty(expected, isEmpty(queueADT), getQueue(queueADT));
    }

    protected void validSize(int expected, ArrayQueueADT queueADT) {
        validSize(expected, size(queueADT), getQueue(queueADT));
    }

    protected void validElement(Object expected, ArrayQueueADT queueADT) {
        validElement(expected, element(queueADT), getQueue(queueADT));
    }

    protected void validDequeue(Object expected, ArrayQueueADT queueADT) {
        validDequeue(expected, dequeue(queueADT), getQueue(queueADT));
    }

    protected void validPeek(Object expected, ArrayQueueADT queueADT) {
        validPeek(expected, peek(queueADT), getQueue(queueADT));
    }

    protected void validRemove(Object expected, ArrayQueueADT queueADT) {
        validRemove(expected, remove(queueADT), getQueue(queueADT));
    }

    private void validEnqueue(Object expected, ArrayQueueADT queueADT) {
        enqueue(queueADT, expected);
        validEnqueue(expected, peek(queueADT), getQueue(queueADT));
    }

    private void validClear(ArrayQueueADT queueADT) {
        clear(queueADT);
        validClear(isEmpty(queueADT));
    }

    private void validPush(Object expected, ArrayQueueADT queueADT) {
        push(queueADT, expected);
        validPush(expected, element(queueADT), getQueue(queueADT));
    }

    private String getQueue(ArrayQueueADT queueADT) {
        return toStr(queueADT);
    }
}
