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

    @Override
    public void enqueue(Object obj) {
        assert obj != null;
        if (size() + 1 == queue.length) {
            increaseCapacity();
        } else if (end == queue.length) {
            end = 0;
        }
        queue[end++] = obj;
    }

    @Override
    public void push(Object obj) {
        assert obj != null;
        if (size() + 1 == queue.length) {
            increaseCapacity();
        } else if (start == 0) {
            changeMarks(queue.length, end == 0 ? queue.length : end);
        }
        start = start == 0 ? queue.length : start;
        queue[--start] = obj;
    }

    @Override
    public Object element() {
        assert size() > 0;
        return queue[start];
    }

    @Override
    public Object peek() {
        assert size() > 0;
        return queue[end - 1];
    }

    @Override
    public Object dequeue() {
        assert start != end;
        Object result = queue[start];
        queue[start++] = null;
        if (start == queue.length) {
            changeMarks(0, end == queue.length ? 0 : end);
        }
        return result;
    }

    @Override
    public Object remove() {
        assert start != end;
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
    private void increaseCapacity() {
        Object[] newQueue = copy(queue.length * 2);
        changeMarks(0, queue.length - 1);
        queue = newQueue;
    }
    // Post: (∀ i = 1 to n: queueActual[i]' = queueActual[i - 1]) && queue.length' = queue.length * 2

    // Pre: true
    private void changeMarks(int s, int e) {
        start = s;
        end = e;
    }
    // Post: start = s && end = e;

    // Pre: length >= n
    private Object[] copy(int length) {
        assert length >= size();
        Object[] newQueue = new Object[length];
        int firstPart = Math.min(start + size(), queue.length) - start;
        System.arraycopy(queue, start, newQueue, 0, firstPart);
        System.arraycopy(queue, 0, newQueue, firstPart, size() - firstPart);
        return newQueue;
    }
    // Post: (∀ i = 0 to n - 1: R[i] = queueActual[i]) && R.length = length && (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i])
}
