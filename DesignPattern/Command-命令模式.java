public class Receiver {
    public void doSomething() {
        System.out.println("Receiver doSomething...");
    }
}

public class Command {
    private Receiver receiver;

    public Command(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.doSomething();
    }
}

public class Invoker {
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public void action() {
        command.execute();
    }
}

// --------------------- test code

public static void main(String[] args) {
    Receiver receiver = new Receiver();
    Command command = new Command(receiver);
    Invoker invoker = new Invoker(command);

    invoker.action();
}
