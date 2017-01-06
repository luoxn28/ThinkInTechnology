// --------------------- abstract class

public abstract class Man {
    public abstract void sayHello();
    public abstract void sayBye();

    public void talk() {
        sayHello();
        sayBye();
    }
}

// --------------------- concrete class

public class BlackMan extends Man {
    public void sayHello() {
        System.out.println("BlackMan sayHello...");
    }

    public void sayBye() {
        System.out.println("BlackMan sayBye...");
    }
}

public class WhiteMan extends Man {
    public void sayHello() {
        System.out.println("WhiteMan sayHello...");
    }

    public void sayBye() {
        System.out.println("WhiteMan sayBye...");
    }
}

// --------------------- test code

public static void main(String[] args) {
    Man blackMan = new BlackMan();
    Man whiteMan = new WhiteMan();

    blackMan.talk();
    whiteMan.talk();
}
