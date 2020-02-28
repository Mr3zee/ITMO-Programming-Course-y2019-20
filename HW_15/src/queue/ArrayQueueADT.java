package queue;

public class ArrayQueueADT {
    private int start = 0;
    private int end = 0;
    private Object[] queue = new Object[10];

    // queueActual := actual queue (queueADT), not the Object[] queue
    // n := size() (size of the queueActual)
    // R := result of the function

    // Invariant: (∀ i = 1 to n: queue[i] != null) && n >= 0

    // Pre: obj != null
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
    // Post: (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i]) && queueActual[n] = obj

    // Pre: obj != null
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
    // Post: (∀ i = 1 to n: queueActual[i]' = queueActual[i - 1]) && queueActual[0] = obj

    // Pre: true
    private static void increaseCapacity(ArrayQueueADT queueADT) {
        int length = queueADT.queue.length;
        Object[] newQueue = new Object[length * 2];
        int firstPart = Math.min(queueADT.start + size(queueADT), length) - queueADT.start;
        System.arraycopy(queueADT.queue, queueADT.start, newQueue, 0, firstPart);
        System.arraycopy(queueADT.queue, 0, newQueue, firstPart, size(queueADT) - firstPart);
        changeMarks(queueADT, 0, length - 1);
        queueADT.queue = newQueue;
    }
    // Post: (∀ i = 1 to n: queueActual[i]' = queueActual[i - 1]) && queue.length' = queue.length * 2

    // Pre: n > 0
    public static Object element(ArrayQueueADT queueADT) {
        assert size(queueADT) > 0;
        return queueADT.queue[queueADT.start];
    }
    // Post: R = queueActual[0] && (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i])

    // Pre: n > 0
    public static Object peek(ArrayQueueADT queueADT) {
        assert size(queueADT) > 0;
        return queueADT.queue[queueADT.end - 1];
    }
    // Post: R = queueActual[n - 1] && (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i])

    // Pre: n > 0
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
    // Post: R = queueActual[0] && (∀ i = 0 to n - 2: queueActual[i]' = queueActual[i + 1])

    // Pre: n > 0
    public static Object remove(ArrayQueueADT queueADT) {
        assert queueADT.start != queueADT.end;
        Object result = queueADT.queue[queueADT.end - 1];
        queueADT.queue[queueADT.end-- -1] = null;
        if (queueADT.end == 0 && queueADT.start != 0) {
            queueADT.end = queueADT.queue.length;
        }
        return result;
    }
    // Post: R = queueActual[n - 1] && (∀ i = 0 to n - 2: queueActual[i]' = queueActual[i])

    // Pre: true
    public static int size(ArrayQueueADT queueADT) {
        return queueADT.end < queueADT.start ? (queueADT.queue.length - queueADT.start + queueADT.end) : (queueADT.end - queueADT.start);
    }
    // Post: R = n && (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i])

    // Pre: true
    public static boolean isEmpty(ArrayQueueADT queueADT) {
        return queueADT.start == queueADT.end;
    }
    // Post: R = (n > 0) && (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i])

    // Pre: true
    public static void clear(ArrayQueueADT queueADT) {
        queueADT.queue = new Object[10];
        changeMarks(queueADT, 0, 0);
    }
    // Post: n = 0

    // Pre: true
    private static void changeMarks(ArrayQueueADT queueADT, int s, int e) {
        queueADT.start = s;
        queueADT.end = e;
    }
    // Post: start = s && end = e;

    // Pre: true
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
    // Post: R = ('[' + queueActual[0] + ", " + .. + ", " + queueActual[n - 1] + ']') && (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i])
}
