import org.junit.Assert;
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
        ArrayQueueModule.dequeue();

        ArrayQueueModule.enqueue(value3());
        validElement(value3());
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
        validEnqueue(value2());
        validEnqueue(value1());
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
        ArrayQueueModule.enqueue(value1());
        ArrayQueueModule.enqueue(value2());
        validDequeue("123");
        validDequeue("124");
        validDequeue(false);
        validDequeue("125");
        validDequeue(true);
        validDequeue("126");
        validDequeue("hello");
    }

    @Override
    public void doubleQueueTest() {
        System.out.println("DoubleQueueTest is not supported");
    }

    private void validIsEmpty(boolean expected) {
        Assert.assertEquals(expected, ArrayQueueModule.isEmpty());
        System.out.println(isEmptyMessage(expected, getQueue()));
    }

    private void validSize(int expected) {
        Assert.assertEquals(expected, ArrayQueueModule.size());
        System.out.println(sizeMessage(expected, getQueue()));
    }

    private void validEnqueue(Object element) {
        ArrayQueueModule.enqueue(element);
        Assert.assertEquals(element, ArrayQueueModule.lastElement());
        System.out.println(enqueueMessage(element, getQueue()));
    }

    private void validElement(Object expected) {
        Assert.assertEquals(expected, ArrayQueueModule.element());
        System.out.println(elementMessage(expected, getQueue()));
    }

    private void validClear() {
        ArrayQueueModule.clear();
        Assert.assertTrue(ArrayQueueModule.isEmpty());
        System.out.println(clearMessage());
    }

    private void validDequeue(Object expected) {
        Assert.assertEquals(expected, ArrayQueueModule.dequeue());
        System.out.println(dequeueMessage(expected, getQueue()));
    }

    private String getQueue() {
        return messageColor(ArrayQueueModule.makeString());
    }
}
