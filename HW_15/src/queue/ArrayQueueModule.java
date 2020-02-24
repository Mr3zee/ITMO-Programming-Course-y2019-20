package queue;

import java.util.Arrays;

public class ArrayQueueModule {
    private static int start = 0;
    private static int end = 0;
    private static Object[] queue = new Object[10];

    public static void enqueue(Object obj) {
        if ((end == queue.length && start == 0) || (end + 1 == start)) {
            increaseCapacity();
        } else if (end == queue.length) {
            end = 0;
        }
        queue[end++] = obj;
    }

    private static void increaseCapacity() {
        if (end < start) {
            Object[] newQueue = new Object[queue.length * 2];
            System.arraycopy(queue, start, newQueue, 0, queue.length - start);
            System.arraycopy(queue, 0, newQueue, queue.length - start + 1, end);
            start = 0;
            end = queue.length - 1;
            queue = newQueue;
        } else {
            queue = Arrays.copyOf(queue, queue.length * 2);
        }
    }

    public static Object element() {
        assert size() > 0;
        return queue[start];
    }

    public static Object dequeue() {
        assert start != end;
        Object result = queue[start];
        queue[start++] = null;
        if (start == queue.length) {
            start = 0;
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
    }

    public static Object lastElement() {
        assert !isEmpty();
        return queue[end - 1];
    }

    public static String makeString() {
        StringBuilder string = new StringBuilder("[ ");
        if (!isEmpty()) {
            string.append(queue[start]);
            if (end < start) {
                for (int i = start + 1; i < queue.length; i++) {
                    string.append("; ").append(queue[i]);
                }
                for (int i = 0; i < end + 1; i++) {
                    string.append("; ").append(queue[i]);
                }
            } else {
                for (int i = start + 1; i < end; i++) {
                    string.append("; ").append(queue[i]);
                }
            }
        }
        return string.append(" ]").toString();
    }
}
