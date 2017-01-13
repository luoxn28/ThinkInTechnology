// --------------------- 实现化角色和具体实现化角色

public interface Implementor {
    void doSomething();
    void doAnything();
}

public class ImplementorImpl implements Implementor {
    public void doSomething() {
        System.out.println("ImplementorImpl doSomething...");
    }

    public void doAnything() {
        System.out.println("ImplementorImpl doAnything...");
    }
}

// --------------------- 抽象化角色和具体抽象化角色

public abstract class Abstraction {
    Implementor implementor;

    public Abstraction(Implementor implementor) {
        this.implementor = implementor;
    }

    public void request() {
        implementor.doSomething();
    }
}

public class ConcreteAbstraction extends Abstraction {
    public ConcreteAbstraction(Implementor implementor) {
        super(implementor);
    }

    public void request() {
        implementor.doSomething();
        implementor.doAnything();
    }
}

// --------------------- test code

public static void main(String[] args) {
    Implementor implementor = new ImplementorImpl();
    Abstraction abstraction = new ConcreteAbstraction(implementor);

    abstraction.request();
}