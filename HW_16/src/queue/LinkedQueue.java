package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public class LinkedQueue extends AbstractQueue {
    private Node start;
    private Node end;
    private int size;

    public LinkedQueue() {
        clear();
    }

    // Pre: true
    // Post: (∀ i = 0 to n - 1: queue[i]' = queue[i]) && queue[n] = obj
    @Override
    protected void enqueueImpl(Object obj) {
        if (addFirstElement(obj)) return;
        end.next = new Node(null, end, obj);
        end = end.next;
        size++;
    }

    // Pre: true
    // Post: (∀ i = 1 to n: queue[i]' = queue[i - 1]) && queue[0] = obj
    @Override
    protected void pushImpl(Object obj) {
        if (addFirstElement(obj)) return;
        start.prev = new Node(start, null, obj);
        start = start.prev;
        size++;
    }

    // Pre: true
    // Post: R = queue[0] && (∀ i = 0 to n - 1: queue[i]' = queue[i])
    @Override
    protected Object elementImpl() {
        return start.value;
    }

    // Pre: true
    // Post: R = queue[n - 1] && (∀ i = 0 to n - 1: queue[i]' = queue[i])
    @Override
    protected Object peekImpl() {
        return end.value;
    }

    // Pre: true
    // Post: R = queue[0] && (∀ i = 0 to n - 2: queue[i]' = queue[i + 1])
    @Override
    protected Object dequeueImpl() {
        Object result = start.value;
        start = start.next;
        if (removeLastElement()) return result;
        start.prev = null;
        return result;
    }

    // Pre: true
    // Post: R = queue[n - 1] && (∀ i = 0 to n - 2: queue[i]' = queue[i])
    @Override
    protected Object removeImpl() {
        Object result = end.value;
        end = end.prev;
        if (removeLastElement()) return result;
        end.next = null;
        return result;
    }

    @Override
    protected Queue filterImpl(Predicate<Object> predicate) {
        LinkedQueue newQueue = new LinkedQueue();
        if (size != 0) {
            Node node = start;
            while (node != null) {
                if (predicate.test(node.value)) {
                    newQueue.enqueue(node.value);
                }
                node = node.next;
            }
        }
        return newQueue;
    }

    @Override
    protected Queue mapImpl(Function<Object, Object> function) {
        LinkedQueue newQueue = new LinkedQueue();
        if (size != 0) {
            Node node = start;
            while (node != null) {
                newQueue.enqueue(function.apply(node.value));
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
        StringBuilder string = new StringBuilder("[");
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
        Object[] result = new Object[size];
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
            next = null;
            prev = null;
            value = null;
        }
    }
}
