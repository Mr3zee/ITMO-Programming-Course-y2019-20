package queue;

import java.util.function.Function;

public class ArrayQueue extends AbstractQueue {
    private int start;
    private int end;
    private Object[] queueArr;

    public ArrayQueue() {
        this.start = 0;
        this.end = 0;
        this.queueArr = new Object[10];
    }

    @Override
    protected void enqueueImpl(final Object obj) {
        if (size() + 1 == queueArr.length) {
            increaseCapacity();
        }
        if (end == queueArr.length) {
            end = 0;
        }
        queueArr[end++] = obj;
    }

    @Override
    protected void pushImpl(final Object obj) {
        if (size() + 1 == queueArr.length) {
            increaseCapacity();
        }
        if (start == 0) {
            changeMarks(queueArr.length, end == 0 ? queueArr.length : end);
        }
        queueArr[--start] = obj;
    }

    @Override
    protected Object elementImpl() {
        return queueArr[start];
    }

    @Override
    protected Object peekImpl() {
        return queueArr[end - 1];
    }

    @Override
    protected Object dequeueImpl() {
        Object result = queueArr[start];
        queueArr[start] = null;
        if (++start == queueArr.length) {
            changeMarks(0, end == queueArr.length ? 0 : end);
        }
        return result;
    }

    @Override
    protected Object removeImpl() {
        Object result = queueArr[end - 1];
        queueArr[end - 1] = null;
        if (--end == 0 && start != 0) {
            end = queueArr.length;
        }
        return result;
    }

    @Override
    protected Queue makeQueue(final Function<Object, Object> function) {
        final ArrayQueue newQueue = new ArrayQueue();
        int length = queueArr.length;
        for (int i = 0; i < size(); i++) {
            insert(function, newQueue, queueArr[(start + i) % length]);
        }
        return newQueue;
    }

    @Override
    public int size() {
        return end < start ? (queueArr.length - start + end) : (end - start);
    }

    @Override
    public void clear() {
        queueArr = new Object[10];
        changeMarks(0, 0);
    }

    @Override
    public String toStr() {
        final StringBuilder string = new StringBuilder("[");
        int length = queueArr.length;
        if (!isEmpty()) {
            for (int i = 0; i < size() - 1; i++) {
                string.append(queueArr[(start + i) % length]).append(", ");
            }
            string.append(queueArr[end - 1]);
        }
        return string.append("]").toString();
    }

    @Override
    public Object[] toArray() {
        return copy(size());
    }

    // Pre: true
    // Post: (∀ i ∈ [0; n - 1]: queue[i]' = queue[i]) && queueArr.length' = queueArr.length * 2
    private void increaseCapacity() {
        final Object[] newQueue = copy(queueArr.length * 2);
        changeMarks(0, queueArr.length - 1);
        queueArr = newQueue;
    }

    // Pre: true
    // Post: start = s && end = e;
    private void changeMarks(int s, int e) {
        start = s;
        end = e;
    }

    // Pre: length >= n
    // Post: (∀ i ∈ [0; n - 1]: R[i] = queue[i]) && R.length = length && (∀ i ∈ [0; n - 1]: queue[i]' = queue[i])
    private Object[] copy(int length) {
        assert length >= size();
        final Object[] newQueue = new Object[length];
        int firstPart = Math.min(start + size(), queueArr.length) - start;
        System.arraycopy(queueArr, start, newQueue, 0, firstPart);
        System.arraycopy(queueArr, 0, newQueue, firstPart, size() - firstPart);
        return newQueue;
    }
}
