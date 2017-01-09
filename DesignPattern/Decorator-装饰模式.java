// --------------------- interface

public interface Interface {
    void doSomething();
}

public class Component implements Interface  {
    public void doSomething() {
        System.out.println("Component doSomething...");
    }
}

public class Decorator implements Interface {
    private Interface component;

    public Decorator(Interface component) {
        this.component = component;
    }

    public void doSomething() {
        System.out.println("我要开始装饰了...");
        this.component.doSomething();
    }
}

// --------------------- test code

public static void main(String[] args) {
    Interface component = new Component();
    Decorator proxy = new Decorator(component);

    proxy.doSomething();
}
