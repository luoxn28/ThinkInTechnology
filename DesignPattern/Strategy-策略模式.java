// --------------------- interface

public interface Strategy {
    void doSomething();
}

// --------------------- concrete class

public class ConcreteStrategy1 implements Strategy {
    public void doSomething() {
        System.out.println("ConcreteStrategy1 doSomething...");
    }
}

public class ConcreteStrategy2 implements Strategy {
    public void doSomething() {
        System.out.println("ConcreteStrategy1 doSomething...");
    }
}

// --------------------- context class

public class Context {
    private Strategy strategy = null;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void doAnything() {
        this.strategy.doSomething();
    }
}

// --------------------- test code

public static void main(String[] args) {
    Strategy strategy1 = new ConcreteStrategy1();
    Context context = new Context(strategy1);

    context.doAnything();
}
