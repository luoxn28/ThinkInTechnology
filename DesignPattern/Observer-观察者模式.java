// --------------------- 被观察者

public abstract class Subject {
    private Vector<Observer> obsVectors = new Vector<Observer>();

    public void addObserver(Observer o) {
        obsVectors.add(o);
    }

    public void delObserver(Observer o) {
        obsVectors.remove(o);
    }

    public void notifyObservers() {
        for (Observer o : obsVectors) {
            o.update();
        }
    }

    public abstract void doSomething();
}

// --------------------- 具体被观察者

public class ConcreteSubject extends Subject {
    public void doSomething() {
        System.out.println("ConcreteSubject doSomething...");

        super.notifyObservers();
    }
}

// --------------------- 观察者

public class Observer {
    public void update() {
        System.out.println("Observer update...");
    }
}

// --------------------- test code

public static void main(String[] args) {
    Observer observer = new Observer();
    Subject subject = new ConcreteSubject();

    subject.addObserver(observer);
    subject.doSomething();
}
