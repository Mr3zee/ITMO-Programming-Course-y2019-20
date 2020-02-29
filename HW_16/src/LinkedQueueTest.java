import queue.LinkedQueue;

public class LinkedQueueTest extends AbstractTest {
    @Override
    protected void abstractSetUp() {
        queue = new LinkedQueue();
    }

    @Override
    public String testName() {
        return "Linked Queue";
    }
}
