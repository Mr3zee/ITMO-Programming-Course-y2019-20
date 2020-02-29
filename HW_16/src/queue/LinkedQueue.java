package queue;

public class LinkedQueue extends AbstractQueue {
    private Node start;
    private Node end;
    private int size;

    public LinkedQueue() {
        clear();
    }

    @Override
    public void enqueue(Object obj) {
        assert obj != null;
        if (addFirstElement(obj)) return;
        end.next = new Node(null, end, obj);
        end = end.next;
        size++;
    }

    @Override
    public void push(Object obj) {
        assert obj != null;
        if (addFirstElement(obj)) return;
        start.prev = new Node(start, null, obj);
        start = start.prev;
        size++;
    }

    private boolean addFirstElement(Object obj) {
        if (size == 0) {
            start = new Node(null, null, obj);
            end = start;
            size++;
            return true;
        }
        return false;
    }

    @Override
    public Object element() {
        assert size > 0;
        return start.value;
    }

    @Override
    public Object peek() {
        assert size > 0;
        return end.value;
    }

    @Override
    public Object dequeue() {
        assert size > 0;
        Object result = start.value;
        start = start.next;
        if (removeLastElement()) return result;
        start.prev = null;
        return result;
    }

    @Override
    public Object remove() {
        assert size > 0;
        Object result = end.value;
        end = end.prev;
        if (removeLastElement()) return result;
        end.next = null;
        return result;
    }

    private boolean removeLastElement() {
        if (--size == 0) {
            clear();
            return true;
        }
        return false;
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
        return string.append(node.value.toString()).append("]").toString();
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
