package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
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
        return makeQueue(predicate::test, true);
    }

    @Override
    public Queue map(final Function<Object, Object> function) {
        checkObject(function);
        return makeQueue(function, false);
    }

    // Pre: function != null && queue != null && object != null
    // Post: if function is predicate and it is true: queue.enqueue(object).post
    //       if function is not predicate: queue.enqueue(function.apply(object)).post
    //       else: nothing
    protected void insert(final Function<Object, Object> function, boolean functionType, final Queue queue, Object object) {
        Object value = function.apply(object);
        checkObject(value);
        if (functionType) {
            if ((boolean) value) {
                queue.enqueue(object);
            }
        } else {
            queue.enqueue(value);
        }
    }

    // Pre: function != null
    // Post: if function is predicate : result is the subsequence of this.queue
    //       if function is not predicate : result is this.queue with function applied to it's elements
    protected abstract Queue makeQueue(final Function<Object, Object> function, boolean type);

    // Pre: true
    // Post: (∀ i = 0 to n - 1: queue[i]' = queue[i]) && queue[n] = obj
    protected abstract void enqueueImpl(final Object obj);

    // Pre: true
    // Post: (∀ i = 1 to n: queue[i]' = queue[i - 1]) && queue[0] = obj
    protected abstract void pushImpl(final Object obj);

    // Pre: true
    // Post: R = queue[0] && (∀ i = 0 to n - 1: queue[i]' = queue[i])
    protected abstract Object elementImpl();

    // Pre: true
    // Post: R = queue[n - 1] && (∀ i = 0 to n - 1: queue[i]' = queue[i])
    protected abstract Object peekImpl();

    // Pre: true
    // Post: R = queue[0] && (∀ i = 0 to n - 2: queue[i]' = queue[i + 1])
    protected abstract Object dequeueImpl();

    // Pre: true
    // Post: R = queue[n - 1] && (∀ i = 0 to n - 2: queue[i]' = queue[i])
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
