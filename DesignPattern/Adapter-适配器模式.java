// --------------------- 目标角色

public interface Target {
    void doSomething();
}

// --------------------- 源角色

public class Adaptee {
    public void doAnything() {
        System.out.println("Adaptee doAnything...");
    }
}

// --------------------- 适配器角色

public class Adapter extends Adaptee implements Target {
    public void doSomething() {
        super.doAnything();
    }
}

// --------------------- test code

public static void main(String[] args) {
    Target target = new Adapter();

    target.doSomething();
}