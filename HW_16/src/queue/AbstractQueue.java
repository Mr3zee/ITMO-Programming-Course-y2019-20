package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    // R := result of the function

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void enqueue(final Object obj) {
        checkObject(obj);
        enqueueImpl(obj);
    }

    @Override
    public void push(final Object obj) {
        checkObject(obj);
        pushImpl(obj);
    }

    @Override
    public Object element() {
        checkSize();
        return elementImpl();
    }

    @Override
    public Object peek() {
        checkSize();
        return peekImpl();
    }

    @Override
    public Object dequeue() {
        checkSize();
        return dequeueImpl();
    }

    @Override
    public Object remove() {
        checkSize();
        return removeImpl();
    }

    @Override
    public Queue filter(final Predicate<Object> predicate) {
        checkObject(predicate);
        return makeQueue(result -> predicate.test(result) ? result : null);
    }

    @Override
    public Queue map(final Function<Object, Object> function) {
        checkObject(function);
        return makeQueue(function);
    }

    // k := newQueue.length
    //
    // Pre: function != null && newQueue != null && object != null
    // Post: (∀ i ∈ [0; n - 1]: queue[i]' = queue[i]) && (∀ j ∈ [0; k - 1]: newQueue[j]' = newQueue[j]) &&
    //       ((function.apply(object) != null && newQueue[k] = obj) || function.apply(object) = null)
    protected void insert(final Function<Object, Object> function, final Queue newQueue, Object object) {
        Object value = function.apply(object);
        if (value != null) {
            newQueue.enqueue(value);
        }
    }

    // Pre: function != null
    // Post: R = {queue[j] | (j ∈ i1 .. ik, 0 <= i1 < i2 < .. < ik < n) &&
    //       (∀ j: function.apply(queue[j]) != null) && (∀ p ∈ [0; n - 1] && p != j : function.apply(queue[p]) = null)} &&
    //       (∀ i ∈ [0; n - 1]: queue[i]' = queue[i])
    protected abstract Queue makeQueue(final Function<Object, Object> function);

    // Pre: true
    // Post: (∀ i ∈ [0; n - 1]: queue[i]' = queue[i]) && queue[n] = obj
    protected abstract void enqueueImpl(final Object obj);

    // Pre: true
    // Post: (∀ i ∈ [1; n]: queue[i]' = queue[i - 1]) && queue[0] = obj
    protected abstract void pushImpl(final Object obj);

    // Pre: true
    // Post: R = queue[0] && (∀ i ∈ [0; n - 1]: queue[i]' = queue[i])
    protected abstract Object elementImpl();

    // Pre: true
    // Post: R = queue[n - 1] && (∀ i ∈ [0; n - 1]: queue[i]' = queue[i])
    protected abstract Object peekImpl();

    // Pre: true
    // Post: R = queue[0] && (∀ i ∈ [0; n - 2]: queue[i]' = queue[i + 1])
    protected abstract Object dequeueImpl();

    // Pre: true
    // Post: R = queue[n - 1] && (∀ i ∈ [0; n - 2]: queue[i]' = queue[i])
    protected abstract Object removeImpl();

    // Pre: true
    // Post: queue.size > 0
    protected void checkSize(){
        assert size() > 0;
    }

    // Pre: true
    // Post: obj != null
    protected void checkObject(final Object obj) {
        assert obj != null;
    }
}
