package queue;

public class ArrayQueueADT {
    private int start = 0;
    private int end = 0;
    private Object[] queue = new Object[10];

    public static void enqueue(ArrayQueueADT queueADT, Object obj) {
        assert obj != null;
        int length = queueADT.queue.length;
        if (size(queueADT) + 1 == length) {
            increaseCapacity(queueADT);
        } else if (queueADT.end == length) {
            queueADT.end = 0;
        }
        queueADT.queue[queueADT.end++] = obj;
    }

    public static void push(ArrayQueueADT queueADT, Object obj) {
        assert obj != null;
        int length = queueADT.queue.length;
        if (size(queueADT) + 1 == length) {
            increaseCapacity(queueADT);
        } else if (queueADT.start == 0) {
            changeMarks(queueADT, length, queueADT.end == 0 ? length : queueADT.end);
        }
        queueADT.start = queueADT.start == 0 ? queueADT.queue.length : queueADT.start;
        queueADT.queue[--queueADT.start] = obj;
    }

    private static void increaseCapacity(ArrayQueueADT queueADT) {
        int length = queueADT.queue.length;
        Object[] newQueue = new Object[length * 2];
        int firstPart = Math.min(queueADT.start + size(queueADT), length) - queueADT.start;
        System.arraycopy(queueADT.queue, queueADT.start, newQueue, 0, firstPart);
        System.arraycopy(queueADT.queue, 0, newQueue, firstPart, size(queueADT) - firstPart);
        changeMarks(queueADT, 0, length - 1);
        queueADT.queue = newQueue;
    }

    public static Object element(ArrayQueueADT queueADT) {
        assert size(queueADT) > 0;
        return queueADT.queue[queueADT.start];
    }

    public static Object peek(ArrayQueueADT queueADT) {
        assert size(queueADT) > 0;
        return queueADT.queue[queueADT.end - 1];
    }

    public static Object dequeue(ArrayQueueADT queueADT) {
        int length = queueADT.queue.length;
        assert queueADT.start != queueADT.end;
        Object result = queueADT.queue[queueADT.start];
        queueADT.queue[queueADT.start++] = null;
        if (queueADT.start == length) {
            changeMarks(queueADT, 0, queueADT.end == length ? 0 : queueADT.end);
        }
        return result;
    }

    public static Object remove(ArrayQueueADT queueADT) {
        assert queueADT.start != queueADT.end;
        Object result = queueADT.queue[queueADT.end - 1];
        queueADT.queue[queueADT.end-- -1] = null;
        if (queueADT.end == 0 && queueADT.start != 0) {
            queueADT.end = queueADT.queue.length;
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
        changeMarks(queueADT, 0, 0);
    }

    private static void changeMarks(ArrayQueueADT queueADT, int s, int e) {
        queueADT.start = s;
        queueADT.end = e;
    }

    public static String toStr(ArrayQueueADT queueADT) {
        StringBuilder string = new StringBuilder("[");
        if (!isEmpty(queueADT)) {
            string.append(queueADT.queue[queueADT.start]);
            if (queueADT.end < queueADT.start) {
                for (int i = queueADT.start + 1; i < queueADT.queue.length; i++) {
                    string.append(", ").append(queueADT.queue[i]);
                }
                for (int i = 0; i < queueADT.end; i++) {
                    string.append(", ").append(queueADT.queue[i]);
                }
            } else {
                for (int i = queueADT.start + 1; i < queueADT.end; i++) {
                    string.append(", ").append(queueADT.queue[i]);
                }
            }
        }
        return string.append("]").toString();
    }
}
