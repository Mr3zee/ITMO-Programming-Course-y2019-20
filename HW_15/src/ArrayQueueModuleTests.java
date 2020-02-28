import queue.ArrayQueueModule;

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

        for (int i = 0; i < 300; i++) {
            ArrayQueueModule.enqueue("hi");
        }
        validElement(true);
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
        ArrayQueueModule.clear();
        for (int i = 0; i < 10; i++) {
            validEnqueue(123 - i);
            ArrayQueueModule.dequeue();
        }
        validEnqueue(1);

        ArrayQueueModule.clear();
        ArrayQueueModule.enqueue(1);
        for (int i = 0; i < 3; i++) {
            ArrayQueueModule.enqueue(1);
            ArrayQueueModule.dequeue();
        }
        for (int i = 0; i < 1500; i++) {
            validEnqueue(i);
        }
        validEnqueue("hello");
    }

    @Override
    public void clearTest() {
        validClear();
        for (int i = 0; i < 100000; i++) {
            ArrayQueueModule.enqueue(i);
        }
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

    @Override
    public void pushTest() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 10; j++) {
                validPush(j + "world");
            }
            validPush("hello" + i);
            for (int j = 0; j < 5; j++) {
                ArrayQueueModule.remove();
            }
        }
    }

    @Override
    public void peekTest() {
        for (int i = 0; i < 1500; i++) {
            ArrayQueueModule.enqueue(22);
            validPeek(22);
        }
        ArrayQueueModule.dequeue();
        ArrayQueueModule.enqueue("hello");
        validPeek("hello");
    }

    @Override
    public void removeTest() {
        for (int i = 0; i < 20; i++) {
            ArrayQueueModule.push(i);
        }
        for (int i = 0; i < 20; i++) {
            validRemove(i);
        }
        ArrayQueueModule.push("hello");
        ArrayQueueModule.push("world");
        validRemove("hello");
        validRemove("world");
    }

    @Override
    public void doubleQueueTest() {
        System.out.println("DoubleQueueTest is not supported");
    }

    @Override
    public void toArrayTest() {
        validToArray(new Object[]{});
        ArrayQueueModule.enqueue(1);
        ArrayQueueModule.enqueue(2);
        ArrayQueueModule.enqueue("hello");
        ArrayQueueModule.push("world");
        validToArray(new Object[]{"world", 1, 2, "hello"});
        ArrayQueueModule.dequeue();
        validToArray(new Object[]{1, 2, "hello"});
        ArrayQueueModule.remove();
        validToArray(new Object[]{1, 2});
        ArrayQueueModule.clear();
        validToArray(new Object[]{});
    }

    @Override
    public void toStrTest() {
        validToStr("[]");
        ArrayQueueModule.enqueue(1);
        ArrayQueueModule.enqueue(2);
        ArrayQueueModule.enqueue("hello");
        ArrayQueueModule.push("world");
        validToStr("[world, 1, 2, hello]");
        ArrayQueueModule.dequeue();
        validToStr("[1, 2, hello]");
        ArrayQueueModule.remove();
        validToStr("[1, 2]");
        ArrayQueueModule.clear();
        validToStr("[]");
    }

    private void validIsEmpty(boolean expected) {
        validIsEmpty(expected, ArrayQueueModule.isEmpty(), getQueue());
    }

    private void validSize(int expected) {
        validSize(expected, ArrayQueueModule.size(), getQueue());
    }

    private void validElement(Object expected) {
        validElement(expected, ArrayQueueModule.element(), getQueue());
    }

    private void validDequeue(Object expected) {
        validDequeue(expected, ArrayQueueModule.dequeue(), getQueue());
    }

    private void validPeek(Object expected) {
        validPeek(expected, ArrayQueueModule.peek(), getQueue());
    }

    private void validRemove(Object expected) {
        validRemove(expected, ArrayQueueModule.remove(), getQueue());
    }

    private void validToStr(Object expected) {
        validToStr(expected, ArrayQueueModule.toStr(), getQueue());
    }

    private void validToArray(Object[] expected) {
        validToArray(expected, ArrayQueueModule.toArray(), getQueue());
    }

    private void validEnqueue(Object expected) {
        ArrayQueueModule.enqueue(expected);
        validEnqueue(expected, ArrayQueueModule.peek(), getQueue());
    }

    private void validClear() {
        ArrayQueueModule.clear();
        validClear(ArrayQueueModule.isEmpty());
    }

    private void validPush(Object expected) {
        ArrayQueueModule.push(expected);
        validPush(expected, ArrayQueueModule.element(), getQueue());
    }

    private String getQueue() {
        return ArrayQueueModule.toStr();
    }
}