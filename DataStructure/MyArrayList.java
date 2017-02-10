import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * MyArrayList 集合类
 */
public class MyArrayList<T> implements Iterable<T> {
    private static int DEFAULT_CAPACITY = 10;

    private T[] items;
    private int size;

    public MyArrayList() {
        clear();
    }

    // ------------------ public scope

    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return this.items[index];
    }

    public T set(int index, T newValue) {
        if (index < 0 || index >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }

        T old = this.items[index];
        this.items[index] = newValue;

        return old;
    }

    public void add(T value) {
        add(size(), value);
    }

    public void add(int idx, T value) {
        if (idx < 0 || (idx > 2 * size())) {
            throw new ArrayIndexOutOfBoundsException();
        }

        if (this.items.length == size()) {
            ensureCapacity(2 * size() + 1);
        }
        for (int i = size(); i > idx; i--) {
            this.items[i] = this.items[i - 1];
        }
        this.items[idx] = value;

        this.size++;
    }

    public void remove(int idx) {
        if (idx < 0 || idx >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        for (int i = idx;  i < size() - 1; i++) {
            this.items[i] = this.items[i + 1];
        }

        this.size--;
    }

    public int size() {
        return this.size;
    }
    public void clear() {
        doClear();
    }
    public void trimToSize() {
        ensureCapacity(size());
    }

    public Iterator iterator() {
        return new MyIterator<T>();
    }

    // -------------------- Iterator class

    class MyIterator<T> implements Iterator<T> {
        private int current = 0;

        public boolean hasNext() {
            return (current < size());
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) items[current++];
        }

        public void remove() {
            MyArrayList.this.remove(--current);
        }
    }

    // --------------------- private scope

    private void doClear() {
        this.size = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    private void ensureCapacity(int newSize) {
        if (newSize < size()) {
            return;
        }

        T[] oldItems = this.items;
        this.items = (T[]) new Object[newSize];
        for (int i = 0; i < size(); i++) {
            this.items[i] = oldItems[i];
        }
    }
}
