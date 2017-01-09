// --------------------- 中介者类
public class Mediator {
    protected Colleague1 colleague1;
    protected Colleague2 colleague2;

    public void doSomething() {
        colleague1.doSomething();
    }

    public void doSomethingElse() {
        colleague2.doSomethingElse();
    }

    // ------------------------------

    public Colleague1 getColleague1() {
        return colleague1;
    }

    public void setColleague1(Colleague1 colleague1) {
        this.colleague1 = colleague1;
    }

    public Colleague2 getColleague2() {
        return colleague2;
    }

    public void setColleague2(Colleague2 colleague2) {
        this.colleague2 = colleague2;
    }
}

// --------------------- 与中介者交互的几个类

public class Colleague1 {
    public void doSomething() {
        System.out.println("Colleague1 doSomething...");
    }
}

public class Colleague2 {
    public void doSomethingElse() {
        System.out.println("Colleague2 doSomethingElse...");
    }
}

// --------------------- test code

public static void main(String[] args) {
    Mediator mediator = new Mediator();

    mediator.setColleague1(new Colleague1());
    mediator.setColleague2(new Colleague2());

    mediator.doSomething();
    mediator.doSomethingElse();
}