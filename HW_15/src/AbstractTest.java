import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

    @Test
    public abstract void pushTest();

    @Test
    public abstract void peekTest();

    @Test
    public abstract void removeTest();

    @Test
    public abstract void toArrayTest();

    @Test
    public abstract void toStrTest();

    protected void validIsEmpty(boolean expected, boolean actual, String queue) {
        Assert.assertEquals(expected, actual);
        System.out.println(isEmptyMessage(expected, queue));
    }

    protected void validSize(int expected, int actual, String queue) {
        Assert.assertEquals(expected, actual);
        System.out.println(sizeMessage(expected, queue));
    }

    protected void validEnqueue(Object element, Object actual, String queue) {
        Assert.assertEquals(element, actual);
        System.out.println(enqueueMessage(element, queue));
    }

    protected void validClear(boolean actual) {
        Assert.assertTrue(actual);
        System.out.println(clearMessage());
    }

    protected void validElement(Object expected, Object actual, String queue) {
        Assert.assertEquals(expected, actual);
        System.out.println(elementMessage(expected, queue));
    }

    protected void validDequeue(Object expected, Object actual, String queue) {
        Assert.assertEquals(expected, actual);
        System.out.println(dequeueMessage(expected, queue));
    }

    protected void validPush(Object expected, Object actual, String queue) {
        Assert.assertEquals(expected, actual);
        System.out.println(pushMessage(expected, queue));
    }

    protected void validPeek(Object expected, Object actual, String queue) {
        Assert.assertEquals(expected, actual);
        System.out.println(peekMessage(expected, queue));
    }

    protected void validRemove(Object expected, Object actual, String queue) {
        Assert.assertEquals(expected, actual);
        System.out.println(removeMessage(expected, queue));
    }

    protected void validToStr(Object expected, Object actual, String queue) {
        Assert.assertEquals(expected, actual);
        System.out.println(toStrMessage(queue));
    }

    protected void validToArray(Object[] expected, Object[] actual, String queue) {
        Assert.assertArrayEquals(expected, actual);
        System.out.println(toArrayMessage(queue));
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
}
