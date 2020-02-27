package queue;

public class ArrayQueue {
    private int start;
    private int end;
    private Object[] queue;

    public ArrayQueue() {
        this.start = 0;
        this.end = 0;
        this.queue = new Object[10];
    }

    public void enqueue(Object obj) {
        assert obj != null;
        if (size() + 1 == queue.length) {
            increaseCapacity();
        } else if (end == queue.length) {
            end = 0;
        }
        queue[end++] = obj;
    }

    public void push(Object obj) {
        assert obj != null;
        if (size() + 1 == queue.length) {
            increaseCapacity();
        } else if (start == 0) {
            start = queue.length;
            if (end == 0) {
                end = queue.length;
            }
        }
        start = start == 0 ? queue.length : start;
        queue[--start] = obj;
    }

    private void increaseCapacity() {
        Object[] newQueue = new Object[queue.length * 2];
        int firstPart = Math.min(start + size(), queue.length) - start;
        System.arraycopy(queue, start, newQueue, 0, firstPart);
        System.arraycopy(queue, 0, newQueue, firstPart, size() - firstPart);
        end = queue.length - 1;
        start = 0;
        queue = newQueue;
    }

    public Object element() {
        assert size() > 0;
        return queue[start];
    }

    public Object peek() {
        assert size() > 0;
        return queue[end - 1];
    }

    public Object dequeue() {
        assert start != end;
        Object result = queue[start];
        queue[start++] = null;
        if (start == queue.length) {
            start = 0;
            if (end == queue.length) {
                end = 0;
            }
        }
        return result;
    }

    public Object remove() {
        assert start != end;
        Object result = queue[end - 1];
        queue[end-- -1] = null;
        if (end == 0 && start != 0) {
            end = queue.length;
        }
        return result;
    }

    public int size() {
        return end < start ? (queue.length - start + end) : (end - start);
    }

    public boolean isEmpty() {
        return start == end;
    }

    public void clear() {
        queue = new Object[10];
        start = 0;
        end = 0;
    }

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
}
