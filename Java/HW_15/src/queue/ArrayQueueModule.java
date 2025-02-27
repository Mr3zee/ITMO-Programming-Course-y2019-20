package queue;

public class ArrayQueueModule {
    private static int start = 0;
    private static int end = 0;
    private static Object[] queue = new Object[10];

    // queueActual := actual queue, not the Object[] queue
    // n := size() (size of the queueActual)
    // R := result of the function

    // Invariant: (∀ i = 1 to n: queue[i] != null) && n >= 0

    // Pre: obj != null
    public static void enqueue(Object obj) {
        assert obj != null;
        if (size() + 1 == queue.length) {
            increaseCapacity();
        } else if (end == queue.length) {
            end = 0;
        }
        queue[end++] = obj;
    }
    // Post: (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i]) && queueActual[n] = obj

    // Pre: obj != null
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
    // Post: (∀ i = 1 to n: queueActual[i]' = queueActual[i - 1]) && queueActual[0] = obj


    // Pre: true
    private static void increaseCapacity() {
        Object[] newQueue = copy(queue.length * 2);
        changeMarks(0, queue.length - 1);
        queue = newQueue;
    }
    // Post: (∀ i = 1 to n: queueActual[i]' = queueActual[i - 1]) && queue.length' = queue.length * 2

    // Pre: n > 0
    public static Object element() {
        assert size() > 0;
        return queue[start];
    }
    // Post: R = queueActual[0] && (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i])

    // Pre: n > 0
    public static Object peek() {
        assert size() > 0;
        return queue[end - 1];
    }
    // Post: R = queueActual[n - 1] && (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i])

    // Pre: n > 0
    public static Object dequeue() {
        assert start != end;
        Object result = queue[start];
        queue[start++] = null;
        if (start == queue.length) {
            changeMarks(0, end == queue.length ? 0 : end);
        }
        return result;
    }
    // Post: R = queueActual[0] && (∀ i = 0 to n - 2: queueActual[i]' = queueActual[i + 1])

    // Pre: n > 0
    public static Object remove() {
        assert start != end;
        Object result = queue[end - 1];
        queue[end-- -1] = null;
        if (end == 0 && start != 0) {
            end = queue.length;
        }
        return result;
    }
    // Post: R = queueActual[n - 1] && (∀ i = 0 to n - 2: queueActual[i]' = queueActual[i])

    // Pre: true
    public static int size() {
        return end < start ? (queue.length - start + end) : (end - start);
    }
    // Post: R = n && (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i])

    // Pre: true
    public static boolean isEmpty() {
        return start == end;
    }
    // Post: R = (n > 0) && (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i])

    // Pre: true
    public static void clear() {
        queue = new Object[10];
        changeMarks(0, 0);
    }
    // Post: n = 0

    // Pre: true
    private static void changeMarks(int s, int e) {
        start = s;
        end = e;
    }
    // Post: start = s && end = e;

    // Pre: true
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
    // Post: R = ('[' + queueActual[0] + ", " + .. + ", " + queueActual[n - 1] + ']') && (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i])

    // Pre: true
    public static Object[] toArray() {
        return copy(size());
    }
    // Post: (∀ i = 0 to n - 1: R[i] = queueActual[i]) && R.length = n && (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i])

    // Pre: length >= n
    private static Object[] copy(int length) {
        assert length >= size();
        Object[] newQueue = new Object[length];
        int firstPart = Math.min(start + size(), queue.length) - start;
        System.arraycopy(queue, start, newQueue, 0, firstPart);
        System.arraycopy(queue, 0, newQueue, firstPart, size() - firstPart);
        return newQueue;
    }
    // Post: (∀ i = 0 to n - 1: R[i] = queueActual[i]) && R.length = length && (∀ i = 0 to n - 1: queueActual[i]' = queueActual[i])
}
