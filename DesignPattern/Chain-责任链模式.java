public abstract class Handler {
    private Handler next = null;

    public void setHandler(Handler next) {
        this.next = next;
    }

    public void run() {
        process();
        if (this.next != null) {
            this.next.run();
        }
    }

    public abstract void process();
}

public class RedHandler extends Handler {
    public void process() {
        System.out.println("RedHandler process...");
    }
}

public class BlueHandler extends Handler {
    public void process() {
        System.out.println("BlueHandler process....");
    }
}

public class BlackHandler extends Handler {
    public void process() {
        System.out.println("BlackHandler process...");
    }
}

// --------------------- test code

public static void main(String[] args) throws CloneNotSupportedException {
    Handler redHandler = new RedHandler();
    Handler blueHandler = new BlueHandler();
    Handler blackHandler = new BlackHandler();

    blueHandler.setHandler(blackHandler);
    redHandler.setHandler(blueHandler);

    redHandler.run();
}

