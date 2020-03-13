package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Queue {
    // n := queue.length
    // R := result of the function

    // Invariant: (∀ i ∈ [0; n - 1]: queue[i] != null) && n >= 0

    // Pre: obj != null
    // Post: (∀ i ∈ [0; n - 1]: queue[i]' = queue[i]) && queue[n] = obj
    void enqueue(Object obj);

    // Pre: obj != null
    // Post: (∀ i ∈ [1; n]: queue[i]' = queue[i - 1]) && queue[0] = obj
    void push(Object obj);

    // Pre: n > 0
    // Post: R = queue[0] && (∀ i ∈ [0; n - 1]: queue[i]' = queue[i])
    Object element();

    // Pre: n > 0
    // Post: R = queue[n - 1] && (∀ i ∈ [0; n - 1]: queue[i]' = queue[i])
    Object peek();

    // Pre: n > 0
    // Post: R = queue[0] && (∀ i ∈ [0; n - 2]: queue[i]' = queue[i + 1])
    Object dequeue();

    // Pre: n > 0
    // Post: R = queue[n - 1] && (∀ i ∈ [0; n - 2]: queue[i]' = queue[i])
    Object remove();

    // Pre: true
    // Post: R = n && (∀ i ∈ [0; n - 1]: queue[i]' = queue[i])
    int size();

    // Pre: true
    // Post: R = (n > 0) && (∀ i ∈ [0; n - 1]: queue[i]' = queue[i])
    boolean isEmpty();

    // Pre: true
    // Post: n = 0
    void clear();

    // Pre: true
    // Post: R = ('[' + queue[0] + ", " + .. + ", " + queue[n - 1] + ']') && (∀ i ∈ [0; n - 1]: queue[i]' = queue[i])
    String toStr();

    // Pre: true
    // Post: (∀ i ∈ [0; n - 1]: R[i] = queue[i]) && R.length = n && (∀ i ∈ [0; n - 1]: queue[i]' = queue[i])
    Object[] toArray();

    // Pre: predicate != null
    // Post:  R != null && R = {queue[j] | (j ∈ i1 .. ik, 0 <= i1 < i2 < .. < ik < n) &&
    //        (∀ j: predicate.test(queue[j]) = true) && (∀ p ∈ [0; n - 1] && p != j : predicate.test(queue[p]) = false)} &&
    //        (∀ i ∈ [0; n - 1]: queue[i]' = queue[i])
    Queue filter(final Predicate<Object> predicate);

    // Pre: function != null && ∀ object ∈ queue: function.apply(object) != null
    // Post: ∀ i ∈ [0; n - 1] : R[i] = function.apply(queue[i]) && (∀ i ∈ [0; n - 1]: queue[i]' = queue[i])
    Queue map(final Function<Object, Object> function);
}
