import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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
}
