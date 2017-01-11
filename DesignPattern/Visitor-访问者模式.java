// --------------------- 抽象元素

public abstract class Element {
    public abstract void doSomething();
    public abstract void accept(Visitor visitor);
}

// --------------------- 具体元素

public class ConcreteElement extends Element {
    public void doSomething() {
        System.out.println("ConcreteElement doSomething...");
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// --------------------- 具体访问者

public class Visitor {
    public void visit(ConcreteElement element) {
        element.doSomething();
    }
}

// --------------------- test code

public static void main(String[] args) {
    Visitor visitor = new Visitor();
    Element element = new ConcreteElement();

    element.accept(visitor);
}