import org.junit.Assert;
import queue.ArrayQueue;

public class ArrayQueueTests extends AbstractTest {
    ArrayQueue queue;

    @Override
    protected void abstractSetUp() {
        queue = new ArrayQueue();
    }

    @Override
    public String testName() {
        return "ArrayQueue";
    }

    @Override
    public void isEmptyTest() {
        validIsEmpty(true, queue);
        queue.enqueue(1);
        validIsEmpty(false, queue);
        queue.enqueue(1);
        validIsEmpty(false, queue);
        queue.dequeue();
        validIsEmpty(false, queue);
        queue.dequeue();
        validIsEmpty(true, queue);
        queue.enqueue(10);
        validIsEmpty(false, queue);
        queue.clear();
        validIsEmpty(true, queue);
    }

    @Override
    public void sizeTest() {
        validSize(0, queue);
        queue.enqueue(1);
        validSize(1, queue);
        for (int i = 0; i < 2456; i++) {
            queue.enqueue(i);
        }
        validSize(2457, queue);
        queue.dequeue();
        validSize(2456, queue);
        for (int i = 0; i < 123; i++) {
            queue.dequeue();
        }
        validSize(2333, queue);
        for (int i = 0; i < 132; i++) {
            queue.enqueue(i);
        }
        validSize(2465, queue);
        for (int i = 0; i < 168; i++) {
            queue.enqueue(i);
        }
        validSize(2633, queue);
        queue.clear();
        validSize(0, queue);
    }

    @Override
    public void elementTest() {
        queue.enqueue(1);
        validElement(1, queue);
        queue.dequeue();

        queue.enqueue("el");
        validElement("el", queue);
        queue.dequeue();

        queue.enqueue(true);
        validElement(true, queue);
        queue.dequeue();

        StringBuilder a = new StringBuilder().append("123");
        queue.enqueue(a);
        validElement(a, queue);
        queue.dequeue();

        queue.enqueue(value3());
        validElement(value3(), queue);
    }

    @Override
    public void enqueueTest() {
        validEnqueue(1, queue);
        validEnqueue("sdfg", queue);
        queue.dequeue();
        validEnqueue(1.0, queue);
        validEnqueue(true, queue);
        validEnqueue(false, queue);
        queue.clear();
        validEnqueue("hello", queue);
        validEnqueue(value2(), queue);
        validEnqueue(value1(), queue);

        queue.clear();
        queue.enqueue(1);
        for (int i = 0; i < 3; i++) {
            queue.enqueue(1);
            queue.dequeue();
        }
        for (int i = 0; i < 8; i++) {
            queue.enqueue(2);
        }
        validEnqueue("hello", queue);
    }

    @Override
    public void clearTest() {
        validClear(queue);
        for (int i = 0; i < 100000; i++) {
            queue.enqueue(i);
        }
        validClear(queue);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.dequeue();
        validClear(queue);
        queue.enqueue(2);
        queue.dequeue();
        validClear(queue);
    }

    @Override
    public void dequeueTest() {
        for (int i = 0; i < 25; i++) {
            queue.enqueue(i);
        }
        for (int i = 0; i < 25; i++) {
            validDequeue(i, queue);
        }
        queue.enqueue("123");
        queue.enqueue("124");
        queue.enqueue(false);
        queue.enqueue("125");
        queue.enqueue(true);
        queue.enqueue("126");
        queue.enqueue("hello");
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
        ArrayQueue queue2 = new ArrayQueue();
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

    private void validIsEmpty(boolean expected, ArrayQueue queue) {
        Assert.assertEquals(expected, queue.isEmpty());
        System.out.println(isEmptyMessage(expected, getQueue(queue)));
    }

    private void validSize(int expected, ArrayQueue queue) {
        Assert.assertEquals(expected, queue.size());
        System.out.println(sizeMessage(expected, getQueue(queue)));
    }

    private void validEnqueue(Object element, ArrayQueue queue) {
        queue.enqueue(element);
        Assert.assertEquals(element, queue.lastElement());
        System.out.println(enqueueMessage(element, getQueue(queue)));
    }

    private void validElement(Object expected, ArrayQueue queue) {
        Assert.assertEquals(expected, queue.element());
        System.out.println(elementMessage(expected, getQueue(queue)));
    }

    private void validClear(ArrayQueue queue) {
        queue.clear();
        Assert.assertTrue(queue.isEmpty());
        System.out.println(clearMessage());
    }

    private void validDequeue(Object expected, ArrayQueue queue) {
        Assert.assertEquals(expected, queue.dequeue());
        System.out.println(dequeueMessage(expected, getQueue(queue)));
    }

    private String getQueue(ArrayQueue queue) {
        return messageColor(queue.toStr());
    }
}