package queue;

public interface Queue {
    // n := size o f queue
    // R := result of the function

    // Invariant: (∀ i = 1 to n: queue[i] != null) && n >= 0

    // Pre: obj != null
    void enqueue(Object obj);
    // Post: (∀ i = 0 to n - 1: queue[i]' = queue[i]) && queue[n] = obj

    // Pre: obj != null
    void push(Object obj);
    // Post: (∀ i = 1 to n: queue[i]' = queue[i - 1]) && queue[0] = obj

    // Pre: n > 0
    Object element();
    // Post: R = queue[0] && (∀ i = 0 to n - 1: queue[i]' = queue[i])

    // Pre: n > 0
    Object peek();
    // Post: R = queue[n - 1] && (∀ i = 0 to n - 1: queue[i]' = queue[i])

    // Pre: n > 0
    Object dequeue();
    // Post: R = queue[0] && (∀ i = 0 to n - 2: queue[i]' = queue[i + 1])

    // Pre: n > 0
    Object remove();
    // Post: R = queue[n - 1] && (∀ i = 0 to n - 2: queue[i]' = queue[i])

    // Pre: true
    int size();
    // Post: R = n && (∀ i = 0 to n - 1: queue[i]' = queue[i])

    // Pre: true
    boolean isEmpty();
    // Post: R = (n > 0) && (∀ i = 0 to n - 1: queue[i]' = queue[i])

    // Pre: true
    void clear();
    // Post: n = 0

    // Pre: true
    String toStr();
    // Post: R = ('[' + queue[0] + ", " + .. + ", " + queue[n - 1] + ']') && (∀ i = 0 to n - 1: queue[i]' = queue[i])

    // Pre: true
    Object[] toArray();
    // Post: (∀ i = 0 to n - 1: R[i] = queue[i]) && R.length = n && (∀ i = 0 to n - 1: queue[i]' = queue[i])
}
