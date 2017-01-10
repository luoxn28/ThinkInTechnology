// --------------------- 抽象构件

public abstract class Component {
    public void doSomething() {
        System.out.println("Component doSomething...");
    }
}

// --------------------- 树枝构件

public class Composite extends Component {
    private ArrayList<Component> list = new ArrayList<Component>();

    public void add(Component component) {
        list.add(component);
    }

    public void remove(Component component) {
        list.remove(component);
    }

    public ArrayList<Component> getChildren() {
        return list;
    }
}

public class Leaf extends Component {
    public void doSomething() {
        System.out.println("Leaf doSomething...");
    }
}

// --------------------- 树叶构件
public class Leaf extends Component {
    public void doSomething() {
        System.out.println("Leaf doSomething...");
    }
}

// --------------------- test code

public static void main(String[] args) {
    Composite root = new Composite();
    Composite branch = new Composite();
    Leaf leaf = new Leaf();

    root.add(branch);
    branch.add(leaf);

    root.doSomething();
    display(root);
}

public static void display(Composite root) {
    for (Component ele : root.getChildren()) {
        if (ele instanceof Leaf) {
            ele.doSomething();
        }
        else {
            ele.doSomething();
            display((Composite) ele);
        }
    }
}