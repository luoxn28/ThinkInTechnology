// --------------------- 门面类

public class Facade {
    private ClassA classA = new ClassA();
    private ClassB classB = new ClassB();

    public void methodA() {
        classA.doSomethingA();
    }

    public void methodB() {
        classB.doSomethingB();
    }
}

// --------------------- 子系统类

public class ClassA {
    public void doSomethingA() {
        System.out.println("ClassA doSomethingA...");
    }
}

public class ClassB {
    public void doSomethingB() {
        System.out.println("ClassB doSomethingB...");
    }
}

// --------------------- test code

public static void main(String[] args) {
    Facade facade = new Facade();

    facade.methodA();
    facade.methodB();
}