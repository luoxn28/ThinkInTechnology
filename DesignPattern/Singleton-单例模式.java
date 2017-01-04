/**
 * Singleton - hungry man pattern
 */
public class Singleton {
    private static Singleton singleton = new Singleton();

    private Singleton() { }

    public static Singleton getInstance() {
        return singleton;
    }

    public void hello() {
        System.out.println("hello singleton");
    }
}

/**
 * Singleton2 - idler man pattern
 */
class Singleton2 {
    private static Singleton2 singleton = null;

    private Singleton2() { }

    public static Singleton2 getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton2();
                }
            }
        }

        return singleton;
    }

    public void hello() {
        System.out.println("hello singleton");
    }
}
