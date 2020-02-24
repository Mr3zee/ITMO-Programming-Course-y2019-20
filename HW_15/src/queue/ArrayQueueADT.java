package queue;

import java.util.Arrays;

public class ArrayQueueADT {
    private int start = 0;
    private int end = 0;
    private Object[] queue = new Object[10];

    public static void enqueue(ArrayQueueADT queueADT, Object obj) {
        if ((queueADT.end == queueADT.queue.length && queueADT.start == 0) || (queueADT.end + 1 == queueADT.start)) {
            increaseCapacity(queueADT);
        } else if (queueADT.end == queueADT.queue.length) {
            queueADT.end = 0;
        }
        queueADT.queue[queueADT.end++] = obj;
    }

    private static void increaseCapacity(ArrayQueueADT queueADT) {
        if (queueADT.end < queueADT.start) {
            Object[] newQueue = new Object[queueADT.queue.length * 2];
            System.arraycopy(queueADT.queue, queueADT.start, newQueue, 0, queueADT.queue.length - queueADT.start);
            System.arraycopy(queueADT.queue, 0, newQueue, queueADT.queue.length - queueADT.start + 1, queueADT.end);
            queueADT.start = 0;
            queueADT.end = queueADT.queue.length - 1;
            queueADT.queue = newQueue;
        } else {
            queueADT.queue = Arrays.copyOf(queueADT.queue, queueADT.queue.length * 2);
        }
    }

    public static Object element(ArrayQueueADT queueADT) {
        assert size(queueADT) > 0;
        return queueADT.queue[queueADT.start];
    }

    public static Object dequeue(ArrayQueueADT queueADT) {
        assert queueADT.start != queueADT.end;
        Object result = queueADT.queue[queueADT.start];
        queueADT.queue[queueADT.start++] = null;
        if (queueADT.start == queueADT.queue.length) {
            queueADT.start = 0;
        }
        return result;
    }

    public static int size(ArrayQueueADT queueADT) {
        return queueADT.end < queueADT.start ? (queueADT.queue.length - queueADT.start + queueADT.end) : (queueADT.end - queueADT.start);
    }

    public static boolean isEmpty(ArrayQueueADT queueADT) {
        return queueADT.start == queueADT.end;
    }

    public static void clear(ArrayQueueADT queueADT) {
        queueADT.queue = new Object[10];
        queueADT.start = 0;
        queueADT.end = 0;
    }

    public static Object lastElement(ArrayQueueADT queueADT) {
        assert !isEmpty(queueADT);
        return queueADT.queue[queueADT.end - 1];
    }

    public static String makeString(ArrayQueueADT queueADT) {
        StringBuilder string = new StringBuilder("[ ");
        if (!isEmpty(queueADT)) {
            string.append(queueADT.queue[queueADT.start]);
            if (queueADT.end < queueADT.start) {
                for (int i = queueADT.start + 1; i < queueADT.queue.length; i++) {
                    string.append("; ").append(queueADT.queue[i]);
                }
                for (int i = 0; i < queueADT.end + 1; i++) {
                    string.append("; ").append(queueADT.queue[i]);
                }
            } else {
                for (int i = queueADT.start + 1; i < queueADT.end; i++) {
                    string.append("; ").append(queueADT.queue[i]);
                }
            }
        }
        return string.append(" ]").toString();
    }
}
