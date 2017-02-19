/**
 * priority heap
 */
public class PriorityHeap <T extends Comparable> {
    private static int ARRAY_SIZE = 100;
    private T[] arrays;
    private int size;

    public PriorityHeap() {
        this.arrays = (T[])new Integer[ARRAY_SIZE];
        size = 0;
    }

    public void push(T data) {
        if (size == ARRAY_SIZE) {
            return;
        }

        flip(size++, data);
    }

    public T poll() {
        if (isEmpty()) {
            return null;
        } else {
            T data = this.arrays[0];

            swap(0, this.size - 1);
            this.size--;
            sink(0);

            return data;
        }
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return this.arrays[0];
        }
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return (this.size == 0);
    }

    // ----------------- private methods

    /**
     * flip 元素的上浮操作
     */
    private void flip(int i, T data) {
        while (i > 0) {
            int parent = i / 2;
            int compareResult = data.compareTo(this.arrays[parent]);
            if (compareResult < 0) {
                this.arrays[i] = this.arrays[parent];
            } else {
                break;
            }

            i = parent;
        }

        this.arrays[i] = data;
    }

    /**
     * sink 元素的下沉操作
     */
    private void sink(int i) {
        while ((2 * i + 1) <= (this.size - 1)) {
            int child = 2 * i + 1;
            if ((child < (this.size - 1) && (this.arrays[child].compareTo(this.arrays[child + 1]) > 0))) {
                child++;
            }

            int compareResult = this.arrays[i].compareTo(this.arrays[child]);
            if (compareResult > 0) {
                swap(i, child);
            } else {
                break;
            }

            i = child;
        }
    }

    private void swap(int x, int y) {
        T data = this.arrays[x];
        this.arrays[x] = this.arrays[y];
        this.arrays[y] = data;
    }
}
