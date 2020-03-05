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
    public Queue filter(Predicate<Object> predicate) {
        checkObject(predicate);
        return makeQueue(predicate::test, true);
    }

    @Override
    public Queue map(Function<Object, Object> function) {
        checkObject(function);
        return makeQueue(function, false);
    }

    protected void insert(Function<Object, Object> function, boolean type, Queue newQueue, Object object) {
        Object value = function.apply(object);
        if (type) {
            if ((boolean) value) {
                newQueue.enqueue(object);
            }
        } else {
            newQueue.enqueue(value);
        }
    }

    protected abstract Queue makeQueue(Function<Object, Object> function, boolean type);

    protected abstract void enqueueImpl(Object obj);

    protected abstract void pushImpl(Object obj);

    protected abstract Object elementImpl();

    protected abstract Object peekImpl();

    protected abstract Object dequeueImpl();

    protected abstract Object removeImpl();

    private void checkSize(){
        assert size() > 0;
    }

    private void checkObject(Object obj) {
        assert obj != null;
    }
}
