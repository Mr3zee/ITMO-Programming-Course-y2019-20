package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Queue {
    // n := size o f queue
    // R := result of the function

    // Invariant: (∀ i = 1 to n: queue[i] != null) && n >= 0

    // Pre: obj != null
    // Post: (∀ i = 0 to n - 1: queue[i]' = queue[i]) && queue[n] = obj
    void enqueue(Object obj);

    // Pre: obj != null
    // Post: (∀ i = 1 to n: queue[i]' = queue[i - 1]) && queue[0] = obj
    void push(Object obj);

    // Pre: n > 0
    // Post: R = queue[0] && (∀ i = 0 to n - 1: queue[i]' = queue[i])
    Object element();

    // Pre: n > 0
    // Post: R = queue[n - 1] && (∀ i = 0 to n - 1: queue[i]' = queue[i])
    Object peek();

    // Pre: n > 0
    // Post: R = queue[0] && (∀ i = 0 to n - 2: queue[i]' = queue[i + 1])
    Object dequeue();

    // Pre: n > 0
    // Post: R = queue[n - 1] && (∀ i = 0 to n - 2: queue[i]' = queue[i])
    Object remove();

    // Pre: true
    // Post: R = n && (∀ i = 0 to n - 1: queue[i]' = queue[i])
    int size();

    // Pre: true
    // Post: R = (n > 0) && (∀ i = 0 to n - 1: queue[i]' = queue[i])
    boolean isEmpty();

    // Pre: true
    // Post: n = 0
    void clear();

    // Pre: true
    // Post: R = ('[' + queue[0] + ", " + .. + ", " + queue[n - 1] + ']') && (∀ i = 0 to n - 1: queue[i]' = queue[i])
    String toStr();

    // Pre: true
    // Post: (∀ i = 0 to n - 1: R[i] = queue[i]) && R.length = n && (∀ i = 0 to n - 1: queue[i]' = queue[i])
    Object[] toArray();

    // Pre: predicate != null
    // Post: R is the subsequence of this.queue, with elements satisfying predicate
    Queue filter(final Predicate<Object> predicate);

    // Pre: function != null
    // Post: R is this.queue with function applied to it's elements
    Queue map(final Function<Object, Object> function);
}
