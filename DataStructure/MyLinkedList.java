import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * MYLinkedList
 */
public class MyLinkedList<T> implements Iterable<T> {
    Node<T> list;
    private int size;

    public MyLinkedList() {
        this.list = null;
        this.size = 0;
    }

    // ---------------- public scope

    public void add(T value) {
        add(size(), value);
    }

    public void add(int idx, T value) {
        if (idx <0 || idx > size()) {
            throw new IndexOutOfBoundsException();
        }

        Node node = new Node(value);
        if (this.list == null) {
            this.list = node;
        } else {
            Node nodePrev = null;
            Node nodeCurr = this.list;
            for (int i = 0; i < idx; i++) {
                nodePrev = nodeCurr;
                nodeCurr = nodeCurr.next;
            }

            if (nodeCurr == null) {
                node.prev = nodePrev;
                nodePrev.next = node;
            } else {
                node.prev = nodeCurr.prev;
                node.next = nodeCurr;

                nodeCurr.prev.next = node;
                nodeCurr.prev = node;
            }

        }

        this.size++;
    }

    public T remove(int idx) {
        if (idx < 0 || idx >= size()) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> node = this.list;
        for (int i = 0; i < idx; i++) {
            node = node.next;
        }

        if (node == this.list) {
            this.list = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        }

        this.size--;
        return node.value;
    }

    public T get(int idx) {
        if (idx < 0 || idx >= size()) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> node = this.list;
        for (int i = 0; i < idx; i++) {
            node = node.next;
        }

        return node.value;
    }

    public int size() {
        return this.size;
    }

    public Iterator<T> iterator() {
        return new MyIterator<T>();
    }

    // ---------------- Node class

    class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        public Node(T value) {
            this.value = value;
            prev = next = null;
        }
    }

    // ---------------- Iterator class

    class MyIterator<T> implements Iterator<T> {
        private int current = 0;

        public boolean hasNext() {
            return (this.current < size());
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return (T) get(current++);
        }

        public void remove() {
            MyLinkedList.this.remove(--current);
        }
    }
}
