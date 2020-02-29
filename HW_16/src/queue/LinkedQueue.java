package queue;

public class LinkedQueue extends AbstractQueue {
    private Node start;
    private Node end;
    private int size;

    public LinkedQueue() {
        this(null, null, 0);
    }

    public LinkedQueue(Node start, Node end, int size) {
        this.start = start;
        this.end = end;
        this.size = size;
    }

    @Override
    public void enqueue(Object obj) {
        assert obj != null;
        end.next = new Node(null, end, obj);
        end = end.next;
        size++;
    }

    @Override
    public void push(Object obj) {
        assert obj != null;
        start.prev = new Node(start, null, obj);
        start = start.prev;
        size++;
    }

    @Override
    public Object element() {
        assert size > 0;
        return end.value;
    }

    @Override
    public Object peek() {
        assert size > 0;
        return start.value;
    }

    @Override
    public Object dequeue() {
        assert size > 0;
        Object result = start.value;
        start = start.next;
        size--;
        return result;
    }

    @Override
    public Object remove() {
        assert size > 0;
        Object result = end.value;
        end = end.prev;
        size--;
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        start = null;
        end = null;
        size = 0;
    }

    @Override
    public String toStr() {
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
        Object[] result = new Object[size];
        Node node = start;
        for (Object obj : result) {
            obj = node.value;
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
    }
}
