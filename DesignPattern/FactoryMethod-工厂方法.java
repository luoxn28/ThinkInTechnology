// --------------------- man

public interface Man {
    void talk();
}

public class WhiteMan implements Man {
    public void talk() {
        System.out.println("WhiteMan talking");
    }
}

public class BlackMan implements Man {
    public void talk() {
        System.out.println("BlackMan talking");
    }
}

// --------------------- man factory

public interface ManFactory {
    Man create();
}

public class WhiteManFactory implements ManFactory {
    public Man create() {
        return new WhiteMan();
    }
}

public class BlackManFactory implements ManFactory {
    public BlackMan create() {
        return new BlackMan();
    }
}

// --------------------- test code

public static void main(String[] args) throws InterruptedException {
	ManFactory blackFactory = new BlackManFactory();
	ManFactory whiteFactory = new WhiteManFactory();

	Man blackMan = blackFactory.create();
	Man whiteMan = whiteFactory.create();

	blackMan.talk();
	whiteMan.talk();
}
