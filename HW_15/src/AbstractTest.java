import org.junit.After;
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

    protected String isEmptyMessage(boolean expected, String queue) {
        return "Queue " + Colors.paintPurple(queue) + " is " + (expected ? "" : "not ") + "empty";
    }

    protected String sizeMessage(int expected, String queue) {
        return "Queue " + Colors.paintPurple(queue) + " has size of " + Colors.paintPurple(String.valueOf(expected));
    }

    protected String enqueueMessage(Object element, String queue) {
        return "Queue " + Colors.paintPurple(queue) + " has new last element - " + Colors.paintPurple(element.toString());
    }

    protected String elementMessage(Object expected, String queue) {
        return "Queue " + Colors.paintPurple(queue) + " has " + Colors.paintPurple(expected.toString()) + " as first element";
    }

    protected String clearMessage() {
        return "Queue cleared";
    }

    protected String dequeueMessage(Object expected, String queue) {
        return "Object " + Colors.paintPurple(expected.toString()) + " has been removed from the start of the queue " + Colors.paintPurple(queue);
    }

    protected String pushMessage(Object element, String queue) {
        return "Queue " + Colors.paintPurple(queue) + " has new first element - " + Colors.paintPurple(element.toString());
    }

    protected String peekMessage(Object expected, String queue) {
        return "Queue " + Colors.paintPurple(queue) + " has " + Colors.paintPurple(expected.toString()) + " as last element";
    }

    protected String removeMessage(Object expected, String queue) {
        return "Object " + Colors.paintPurple(expected.toString()) + " has been removed from the end of the queue " + Colors.paintPurple(queue);
    }
}
