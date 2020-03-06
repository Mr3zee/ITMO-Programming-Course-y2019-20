package queue;

import java.util.function.Function;

public class LinkedQueue extends AbstractQueue {
    private Node start;
    private Node end;
    private int size;

    public LinkedQueue() {
        clear();
    }

    @Override
    protected void enqueueImpl(final Object obj) {
        if (addFirstElement(obj)) return;
        end.next = new Node(null, end, obj);
        end = end.next;
        size++;
    }

    @Override
    protected void pushImpl(final Object obj) {
        if (addFirstElement(obj)) return;
        start.prev = new Node(start, null, obj);
        start = start.prev;
        size++;
    }

    @Override
    protected Object elementImpl() {
        return start.value;
    }

    @Override
    protected Object peekImpl() {
        return end.value;
    }

    @Override
    protected Object dequeueImpl() {
        Object result = start.value;
        start = start.next;
        if (removeLastElement()) return result;
        start.prev = null;
        return result;
    }

    @Override
    protected Object removeImpl() {
        Object result = end.value;
        end = end.prev;
        if (removeLastElement()) return result;
        end.next = null;
        return result;
    }

    @Override
    protected Queue makeQueue(final Function<Object, Object> function) {
        final LinkedQueue newQueue = new LinkedQueue();
        if (size != 0) {
            Node node = start;
            while (node != null) {
                insert(function, newQueue, node.value);
                node = node.next;
            }
        }
        return newQueue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        start = new Node();
        end = new Node();
        size = 0;
    }

    @Override
    public String toStr() {
        if (size == 0) {
            return "[]";
        }
        final StringBuilder string = new StringBuilder("[");
        Node node = start;
        while (node.next != null) {
            string.append(node.value.toString()).append(", ");
            node = node.next;
        }
        return string.append(node.value).append("]").toString();
    }

    @Override
    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        }
        final Object[] result = new Object[size];
        Node node = start;
        for (int i = 0; i < size; i++) {
            result[i] = node.value;
            node = node.next;
        }
        return result;
    }

    // Pre: size = 0
    // Post: queue[0] = obj && size = 1
    private boolean addFirstElement(Object obj) {
        if (size == 0) {
            start = new Node(null, null, obj);
            end = start;
            size++;
            return true;
        }
        return false;
    }

    // Pre: true
    // Post: size' = size - 1
    private boolean removeLastElement() {
        if (--size == 0) {
            clear();
            return true;
        }
        return false;
    }

    private static class Node {
        private Node next;
        private Node prev;
        private final Object value;

        public Node(Node next, Node prev, Object value) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }

        public Node() {
            value = null;
        }
    }
}
