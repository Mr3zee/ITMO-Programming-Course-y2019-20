import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import queue.ArrayQueue;
import queue.Queue;

public abstract class AbstractTest {
    protected Queue queue;

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
    public void isEmptyTest() {
        validIsEmpty(true);
        queue.enqueue(1);
        validIsEmpty(false);
        queue.enqueue(1);
        validIsEmpty(false);
        queue.dequeue();
        validIsEmpty(false);
        queue.dequeue();
        validIsEmpty(true);
        queue.enqueue(10);
        validIsEmpty(false);
        queue.clear();
        validIsEmpty(true);
    }

    @Test
    public void sizeTest() {
        validSize(0);
        queue.enqueue(1);
        validSize(1);
        for (int i = 0; i < 2456; i++) {
            queue.enqueue(i);
        }
        validSize(2457);
        queue.dequeue();
        validSize(2456);
        for (int i = 0; i < 123; i++) {
            queue.dequeue();
        }
        validSize(2333);
        for (int i = 0; i < 132; i++) {
            queue.enqueue(i);
        }
        validSize(2465);
        for (int i = 0; i < 168; i++) {
            queue.enqueue(i);
        }
        validSize(2633);
        queue.clear();
        validSize(0);
    }

    @Test
    public void elementTest() {
        queue.enqueue(1);
        validElement(1);
        queue.dequeue();

        queue.enqueue("el");
        validElement("el");
        queue.dequeue();

        queue.enqueue(true);
        validElement(true);
        queue.dequeue();

        StringBuilder a = new StringBuilder().append("123");
        queue.enqueue(a);
        validElement(a);

        for (int i = 0; i < 300; i++) {
            queue.enqueue("hi");
        }
        validElement(a);
    }

    @Test
    public void enqueueTest() {
        validEnqueue(1);
        validEnqueue("sdfg");
        queue.dequeue();
        validEnqueue(1.0);
        validEnqueue(true);
        validEnqueue(false);
        queue.clear();
        validEnqueue("hello");

        queue.clear();
        queue.enqueue(1);
        for (int i = 0; i < 3; i++) {
            queue.enqueue(1);
            queue.dequeue();
        }
        for (int i = 0; i < 1500; i++) {
            validEnqueue(i);
        }
        validEnqueue("hello");
    }

    @Test
    public void clearTest() {
        validClear();
        for (int i = 0; i < 100000; i++) {
            queue.enqueue(i);
        }
        validClear();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.dequeue();
        validClear();
        queue.enqueue(2);
        queue.dequeue();
        validClear();
    }

    @Test
    public void dequeueTest() {
        for (int i = 0; i < 25; i++) {
            queue.enqueue(i);
        }
        for (int i = 0; i < 25; i++) {
            validDequeue(i);
        }
        queue.enqueue("123");
        queue.enqueue("124");
        queue.enqueue(false);
        queue.enqueue("125");
        queue.enqueue(true);
        queue.enqueue("126");
        queue.enqueue("hello");
        validDequeue("123");
        validDequeue("124");
        validDequeue(false);
        validDequeue("125");
        validDequeue(true);
        validDequeue("126");
        validDequeue("hello");
    }

    @Test
    public void pushTest() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 10; j++) {
                validPush(j + "world");
            }
            validPush("hello" + i);
            for (int j = 0; j < 5; j++) {
                queue.remove();
            }
        }
    }

    @Test
    public void peekTest() {
        for (int i = 0; i < 1500; i++) {
            queue.enqueue(22);
            validPeek(22);
        }
        queue.dequeue();
        queue.enqueue("hello");
        validPeek("hello");
    }

    @Test
    public void removeTest() {
        for (int i = 0; i < 2000; i++) {
            queue.push(i);
        }
        for (int i = 0; i < 2000; i++) {
            validRemove(i);
        }
        queue.push("hello");
        queue.push("world");
        validRemove("hello");
        validRemove("world");
    }

    @Test
    public void toArrayTest() {
        validToArray(new Object[]{});
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue("hello");
        queue.push("world");
        validToArray(new Object[]{"world", 1, 2, "hello"});
        queue.dequeue();
        validToArray(new Object[]{1, 2, "hello"});
        queue.remove();
        validToArray(new Object[]{1, 2});
        queue.clear();
        validToArray(new Object[]{});
    }

    @Test
    public void toStrTest() {
        validToStr("[]");
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue("hello");
        queue.push("world");
        validToStr("[world, 1, 2, hello]");
        queue.dequeue();
        validToStr("[1, 2, hello]");
        queue.remove();
        validToStr("[1, 2]");
        queue.clear();
        validToStr("[]");
    }

    protected void validIsEmpty(boolean expected) {
        Assert.assertEquals(expected, queue.isEmpty());
        System.out.println(isEmptyMessage(expected, getQueue()));
    }

    protected void validSize(int expected) {
        Assert.assertEquals(expected, queue.size());
        System.out.println(sizeMessage(expected, getQueue()));
    }

    protected void validEnqueue(Object element) {
        queue.enqueue(element);
        Assert.assertEquals(element, queue.peek());
        System.out.println(enqueueMessage(element, getQueue()));
    }

    protected void validClear() {
        queue.clear();
        Assert.assertTrue(queue.isEmpty());
        System.out.println(clearMessage());
    }

    protected void validElement(Object expected) {
        Assert.assertEquals(expected, queue.element());
        System.out.println(elementMessage(expected, getQueue()));
    }

    protected void validDequeue(Object expected) {
        Assert.assertEquals(expected, queue.dequeue());
        System.out.println(dequeueMessage(expected, getQueue()));
    }

    protected void validPush(Object expected) {
        queue.push(expected);
        Assert.assertEquals(expected, queue.element());
        System.out.println(pushMessage(expected, getQueue()));
    }

    protected void validPeek(Object expected) {
        Assert.assertEquals(expected, queue.peek());
        System.out.println(peekMessage(expected, getQueue()));
    }

    protected void validRemove(Object expected) {
        Assert.assertEquals(expected, queue.remove());
        System.out.println(removeMessage(expected, getQueue()));
    }

    protected void validToStr(Object expected) {
        Assert.assertEquals(expected, queue.toStr());
        System.out.println(toStrMessage(getQueue()));
    }

    protected void validToArray(Object[] expected) {
        Assert.assertArrayEquals(expected, queue.toArray());
        System.out.println(toArrayMessage(getQueue()));
    }

    private String isEmptyMessage(boolean expected, String queue) {
        return "Queue " + Colors.paintPurple(queue) + " is " + (expected ? "" : "not ") + "empty";
    }

    private String sizeMessage(int expected, String queue) {
        return "Queue " + Colors.paintPurple(queue) + " has size of " + Colors.paintPurple(String.valueOf(expected));
    }

    private String enqueueMessage(Object element, String queue) {
        return "Queue " + Colors.paintPurple(queue) + " has new last element - " + Colors.paintPurple(element.toString());
    }

    private String elementMessage(Object expected, String queue) {
        return "Queue " + Colors.paintPurple(queue) + " has " + Colors.paintPurple(expected.toString()) + " as first element";
    }

    private String clearMessage() {
        return "Queue cleared";
    }

    private String dequeueMessage(Object expected, String queue) {
        return "Object " + Colors.paintPurple(expected.toString()) + " has been removed from the start of the queue " + Colors.paintPurple(queue);
    }

    private String pushMessage(Object element, String queue) {
        return "Queue " + Colors.paintPurple(queue) + " has new first element - " + Colors.paintPurple(element.toString());
    }

    private String peekMessage(Object expected, String queue) {
        return "Queue " + Colors.paintPurple(queue) + " has " + Colors.paintPurple(expected.toString()) + " as last element";
    }

    private String removeMessage(Object expected, String queue) {
        return "Object " + Colors.paintPurple(expected.toString()) + " has been removed from the end of the queue " + Colors.paintPurple(queue);
    }

    private String toStrMessage(String queue) {
        return "String for queue is correct: " + Colors.paintPurple(queue);
    }

    private String toArrayMessage(String queue) {
        return "Array for queue is correct: " + Colors.paintPurple(queue);
    }

    private String getQueue() {
        return queue.toStr();
    }
}
