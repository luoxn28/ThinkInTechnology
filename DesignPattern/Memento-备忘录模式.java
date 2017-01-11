// --------------------- 备忘录角色

public class Originator {
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Memento createMemento() {
        return new Memento(state);
    }

    public void restoreMemento(Memento memento) {
        setState(memento.getState());
    }
}

// --------------------- 发起人角色

public class Memento {
    private String state = "";

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

// --------------------- test code

public static void main(String[] args) {
    Originator originator = new Originator();

    originator.setState("luoxn28");

    Memento memento = originator.createMemento();

    originator.setState("");
    originator.restoreMemento(memento);

    System.out.println(originator.getState());
}