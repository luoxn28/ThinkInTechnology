// --------------------- 迭代器接口

public interface Iterator {
    Object next();
    boolean hasNext();
    void remove();
}

// --------------------- 具体迭代器类

public class ConcreteIterator implements Iterator {
    private Vector vector = null;
    private int index = 0;

    public ConcreteIterator(Vector vector) {
        this.vector = vector;
    }

    public Object next() {
        Object result = null;

        if (this.index < vector.size()) {
            result = vector.get(index++);
        }
        return result;
    }

    public boolean hasNext() {
        if (this.index < vector.size()) {
            return true;
        }
        else {
            return false;
        }
    }

    public void remove() {
        vector.remove(index);
    }
}

// --------------------- test code

public static void main(String[] args) {
    Vector vector = new Vector();

    vector.add("hello");
    vector.add("luoxn28");
    vector.add("come on");

    Iterator iter = new ConcreteIterator(vector);
    while (iter.hasNext()) {
        System.out.println(iter.next());
    }
}