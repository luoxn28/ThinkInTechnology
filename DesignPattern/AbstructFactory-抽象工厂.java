// --------------------- two man interface

public interface Male {
    void talk();
}

public interface Female {
    void talk();
}

// --------------------- black man

public class BlackMale implements Male {
    public void talk() {
        System.out.println("BlackMale talking...");
    }
}

public class BlackFemale implements Female {
    public void talk() {
        System.out.println("BlackFemale talking...");
    }
}

// --------------------- white man

public class WhiteMale implements Male {
    public void talk() {
        System.out.println("WhiteMale talking...");
    }
}

public class WhiteFemale implements Female {
    public void talk() {
        System.out.println("WhiteFemale talking...");
    }
}

// --------------------- man factory

public interface Factory {
    Male createMale();
    Female createFemale();
}

public class BlackFactory implements Factory{
    public Male createMale() {
        return new BlackMale();
    }

    public Female createFemale() {
        return new BlackFemale();
    }
}

public class WhiteFactory implements Factory {
    public Male createMale() {
        return new WhiteMale();
    }

    public Female createFemale() {
        return new WhiteFemale();
    }
}

// --------------------- test code

public static void main(String[] args) throws InterruptedException {
	Factory blackFactory = new BlackFactory();
	Factory whiteFactory = new WhiteFactory();

	Male blackMale = blackFactory.createMale();
	Male whiteMale = whiteFactory.createMale();
	Female blackFemale = blackFactory.createFemale();
	Female whiteFemale = whiteFactory.createFemale();

	blackMale.talk();
	whiteMale.talk();
	blackFemale.talk();
	whiteFemale.talk();
}