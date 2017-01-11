// --------------------- 状态接口

public interface State {
    void handle1();
    void handle2();
}

// --------------------- 具体状态

public class ConcreteState1 implements State {
    public void handle1() {
        System.out.println("ConcreteState1 handle1...");
    }

    public void handle2() {
        System.out.println("ConcreteState1 handle2...");
    }
}

public class ConcreteState2 implements State {
        public void handle1() {
            System.out.println("ConcreteState2 handle1...");
        }

        public void handle2() {
            System.out.println("ConcreteState2 handle2...");
        }
}

// --------------------- 环境类

public class Context {
    private State state;

    public Context(State state) {
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void handle1() {
        state.handle1();
    }

    public void handle2() {
        state.handle2();
    }
}

// --------------------- test code

public static void main(String[] args) {
    Context context = new Context(new ConcreteState1());

    context.handle1();
    context.handle2();

    context.setState(new ConcreteState2());

    context.handle1();
    context.handle2();
}