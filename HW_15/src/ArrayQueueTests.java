import queue.ArrayQueue;
import queue.ArrayQueueModule;

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

        for (int i = 0; i < 300; i++) {
            queue.enqueue("hi");
        }
        validElement(a, queue);
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

        queue.clear();
        queue.enqueue(1);
        for (int i = 0; i < 3; i++) {
            queue.enqueue(1);
            queue.dequeue();
        }
        for (int i = 0; i < 1500; i++) {
            validEnqueue(i, queue);
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

    @Override
    public void pushTest() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 10; j++) {
                validPush(j + "world", queue);
            }
            validPush("hello" + i, queue);
            for (int j = 0; j < 5; j++) {
                queue.remove();
            }
        }
    }

    @Override
    public void peekTest() {
        for (int i = 0; i < 1500; i++) {
            queue.enqueue(22);
            validPeek(22, queue);
        }
        queue.dequeue();
        queue.enqueue("hello");
        validPeek("hello", queue);
    }

    @Override
    public void removeTest() {
        for (int i = 0; i < 2000; i++) {
            queue.push(i);
        }
        for (int i = 0; i < 2000; i++) {
            validRemove(i, queue);
        }
        queue.push("hello");
        queue.push("world");
        validRemove("hello", queue);
        validRemove("world", queue);
    }

    @Override
    public void toArrayTest() {
        validToArray(new Object[]{}, queue);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue("hello");
        queue.push("world");
        validToArray(new Object[]{"world", 1, 2, "hello"}, queue);
        queue.dequeue();
        validToArray(new Object[]{1, 2, "hello"}, queue);
        queue.remove();
        validToArray(new Object[]{1, 2}, queue);
        queue.clear();
        validToArray(new Object[]{}, queue);
    }

    @Override
    public void toStrTest() {
        validToStr("[]", queue);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue("hello");
        queue.push("world");
        validToStr("[world, 1, 2, hello]", queue);
        queue.dequeue();
        validToStr("[1, 2, hello]", queue);
        queue.remove();
        validToStr("[1, 2]", queue);
        queue.clear();
        validToStr("[]", queue);
    }

    private void validIsEmpty(boolean expected, ArrayQueue queue) {
        validIsEmpty(expected, queue.isEmpty(), getQueue(queue));
    }

    private void validSize(int expected, ArrayQueue queue) {
        validSize(expected, queue.size(), getQueue(queue));
    }

    private void validElement(Object expected, ArrayQueue queue) {
        validElement(expected, queue.element(), getQueue(queue));
    }

    private void validDequeue(Object expected, ArrayQueue queue) {
        validDequeue(expected, queue.dequeue(), getQueue(queue));
    }

    private void validPeek(Object expected, ArrayQueue queue) {
        validPeek(expected, queue.peek(), getQueue(queue));
    }

    private void validRemove(Object expected, ArrayQueue queue) {
        validRemove(expected, queue.remove(), getQueue(queue));
    }

    private void validToStr(Object expected, ArrayQueue queue) {
        validToStr(expected, queue.toStr(), getQueue(queue));
    }

    private void validToArray(Object[] expected, ArrayQueue queue) {
        validToArray(expected, queue.toArray(), getQueue(queue));
    }

    private void validEnqueue(Object expected, ArrayQueue queue) {
        queue.enqueue(expected);
        validEnqueue(expected, queue.peek(), getQueue(queue));
    }

    private void validClear(ArrayQueue queue) {
        queue.clear();
        validClear(queue.isEmpty());
    }

    private void validPush(Object expected, ArrayQueue queue) {
        queue.push(expected);
        validPush(expected, queue.element(), getQueue(queue));
    }

    private String getQueue(ArrayQueue queue) {
        return queue.toStr();
    }
}
