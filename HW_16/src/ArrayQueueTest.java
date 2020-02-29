import queue.ArrayQueue;

public class ArrayQueueTest extends AbstractTest {
    @Override
    protected void abstractSetUp() {
        queue = new ArrayQueue();
    }

    @Override
    public String testName() {
        return "Array Queue";
    }
}
