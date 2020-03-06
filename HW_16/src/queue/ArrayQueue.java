package queue;

import java.util.function.Function;

public class ArrayQueue extends AbstractQueue {
    private int start;
    private int end;
    private Object[] queue;

    public ArrayQueue() {
        this.start = 0;
        this.end = 0;
        this.queue = new Object[10];
    }

    @Override
    protected void enqueueImpl(final Object obj) {
        if (size() + 1 == queue.length) {
            increaseCapacity();
        }
        if (end == queue.length) {
            end = 0;
        }
        queue[end++] = obj;
    }

    @Override
    protected void pushImpl(final Object obj) {
        if (size() + 1 == queue.length) {
            increaseCapacity();
        }
        if (start == 0) {
            changeMarks(queue.length, end == 0 ? queue.length : end);
        }
        queue[--start] = obj;
    }

    @Override
    protected Object elementImpl() {
        return queue[start];
    }

    @Override
    protected Object peekImpl() {
        return queue[end - 1];
    }

    @Override
    protected Object dequeueImpl() {
        Object result = queue[start];
        queue[start] = null;
        if (++start == queue.length) {
            changeMarks(0, end == queue.length ? 0 : end);
        }
        return result;
    }

    @Override
    protected Object removeImpl() {
        Object result = queue[end - 1];
        queue[end - 1] = null;
        if (--end == 0 && start != 0) {
            end = queue.length;
        }
        return result;
    }

    @Override
    protected Queue makeQueue(final Function<Object, Object> function) {
        final ArrayQueue newQueue = new ArrayQueue();
        int length = queue.length;
        for (int i = 0; i < size(); i++) {
            insert(function, newQueue, queue[(start + i) % length]);
        }
        return newQueue;
    }

    @Override
    public int size() {
        return end < start ? (queue.length - start + end) : (end - start);
    }

    @Override
    public void clear() {
        queue = new Object[10];
        changeMarks(0, 0);
    }

    @Override
    public String toStr() {
        final StringBuilder string = new StringBuilder("[");
        int length = queue.length;
        if (!isEmpty()) {
            for (int i = 0; i < size() - 1; i++) {
                string.append(queue[(start + i) % length]).append(", ");
            }
            string.append(queue[end - 1]);
        }
        return string.append("]").toString();
    }

    @Override
    public Object[] toArray() {
        return copy(size());
    }

    // Pre: true
    // Post: (∀ i = 1 to n: queueActual[i]' = queueActual[i - 1]) && queue.length' = queue.length * 2
    private void increaseCapacity() {
        final Object[] newQueue = copy(queue.length * 2);
        changeMarks(0, queue.length - 1);
        queue = newQueue;
    }

    // Pre: true
    // Post: start = s && end = e;
    private void changeMarks(int s, int e) {
        start = s;
        end = e;
    }

    // Pre: length >= n
    // Post: (∀ i = 0 to n - 1: R[i] = queueActual[i]) && R.length = length && (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i])
    private Object[] copy(int length) {
        assert length >= size();
        final Object[] newQueue = new Object[length];
        int firstPart = Math.min(start + size(), queue.length) - start;
        System.arraycopy(queue, start, newQueue, 0, firstPart);
        System.arraycopy(queue, 0, newQueue, firstPart, size() - firstPart);
        return newQueue;
    }
}
