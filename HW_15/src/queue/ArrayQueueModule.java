package queue;

public class ArrayQueueModule {
    private static int start = 0;
    private static int end = 0;
    private static Object[] queue = new Object[10];

    public static void enqueue(Object obj) {
        assert obj != null;
        if (size() + 1 == queue.length) {
            increaseCapacity();
        } else if (end == queue.length) {
            end = 0;
        }
        queue[end++] = obj;
    }

    public static void push(Object obj) {
        assert obj != null;
        if (size() + 1 == queue.length) {
            increaseCapacity();
        } else if (start == 0) {
            changeMarks(queue.length, end == 0 ? queue.length : end);
        }
        start = start == 0 ? queue.length : start;
        queue[--start] = obj;
    }

    private static void increaseCapacity() {
        Object[] newQueue = new Object[queue.length * 2];
        int firstPart = Math.min(start + size(), queue.length) - start;
        System.arraycopy(queue, start, newQueue, 0, firstPart);
        System.arraycopy(queue, 0, newQueue, firstPart, size() - firstPart);
        changeMarks(0, queue.length - 1);
        queue = newQueue;
    }

    public static Object element() {
        assert size() > 0;
        return queue[start];
    }

    public static Object peek() {
        assert size() > 0;
        return queue[end - 1];
    }

    public static Object dequeue() {
        assert start != end;
        Object result = queue[start];
        queue[start++] = null;
        if (start == queue.length) {
            changeMarks(0, end == queue.length ? 0 : end);
        }
        return result;
    }

    public static Object remove() {
        assert start != end;
        Object result = queue[end - 1];
        queue[end-- -1] = null;
        if (end == 0 && start != 0) {
            end = queue.length;
        }
        return result;
    }

    public static int size() {
        return end < start ? (queue.length - start + end) : (end - start);
    }

    public static boolean isEmpty() {
        return start == end;
    }

    public static void clear() {
        queue = new Object[10];
        start = 0;
        end = 0;
        changeMarks(0, 0);
    }

    private static void changeMarks(int s, int e) {
        start = s;
        end = e;
    }

    public static String toStr() {
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
