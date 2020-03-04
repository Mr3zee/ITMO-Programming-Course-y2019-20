package queue;

public class ArrayQueue extends AbstractQueue {
    private int start;
    private int end;
    private Object[] queue;

    public ArrayQueue() {
        this.start = 0;
        this.end = 0;
        this.queue = new Object[10];
    }

    // Pre: true
    // Post: (∀ i = 0 to n - 1: queue[i]' = queue[i]) && queue[n] = obj
    @Override
    protected void enqueueImpl(Object obj) {
        if (size() + 1 == queue.length) {
            increaseCapacity();
        } else if (end == queue.length) {
            end = 0;
        }
        queue[end++] = obj;
    }

    // Pre: true
    // Post: (∀ i = 1 to n: queue[i]' = queue[i - 1]) && queue[0] = obj
    @Override
    protected void pushImpl(Object obj) {
        if (size() + 1 == queue.length) {
            increaseCapacity();
        } else if (start == 0) {
            changeMarks(queue.length, end == 0 ? queue.length : end);
        }
        start = start == 0 ? queue.length : start;
        queue[--start] = obj;
    }

    // Pre: true
    // Post: R = queue[0] && (∀ i = 0 to n - 1: queue[i]' = queue[i])
    @Override
    protected Object elementImpl() {
        return queue[start];
    }

    // Pre: true
    // Post: R = queue[n - 1] && (∀ i = 0 to n - 1: queue[i]' = queue[i])
    @Override
    protected Object peekImpl() {
        return queue[end - 1];
    }

    // Pre: true
    // Post: R = queue[0] && (∀ i = 0 to n - 2: queue[i]' = queue[i + 1])
    @Override
    protected Object dequeueImpl() {
        Object result = queue[start];
        queue[start++] = null;
        if (start == queue.length) {
            changeMarks(0, end == queue.length ? 0 : end);
        }
        return result;
    }

    // Pre: true
    // Post: R = queue[n - 1] && (∀ i = 0 to n - 2: queue[i]' = queue[i])
    @Override
    protected Object removeImpl() {
        Object result = queue[end - 1];
        queue[end-- - 1] = null;
        if (end == 0 && start != 0) {
            end = queue.length;
        }
        return result;
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
        StringBuilder string = new StringBuilder("[");
        if (!isEmpty()) {
            string.append(queue[start]);
            if (end < start) {
                for (int i = start + 1; i < queue.length; i++) {
                    string.append(", ").append(queue[i]);
                }
                for (int i = 0; i < end; i++) {
                    string.append(", ").append(queue[i]);
                }
            } else {
                for (int i = start + 1; i < end; i++) {
                    string.append(", ").append(queue[i]);
                }
            }
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
        Object[] newQueue = copy(queue.length * 2);
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
        Object[] newQueue = new Object[length];
        int firstPart = Math.min(start + size(), queue.length) - start;
        System.arraycopy(queue, start, newQueue, 0, firstPart);
        System.arraycopy(queue, 0, newQueue, firstPart, size() - firstPart);
        return newQueue;
    }
}
