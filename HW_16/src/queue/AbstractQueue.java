package queue;

public abstract class AbstractQueue implements Queue {
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
}
