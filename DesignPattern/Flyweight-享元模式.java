// --------------------- 享元类

public class Flyweight {
    private String name = "";

    public Flyweight(String name) {
        this.name = name;
    }

    public void hello() {
        System.out.println("hello " + name);
    }
}

// --------------------- 享元工厂

public class FlyweightFactory {
    HashMap<String, Flyweight> pool = new HashMap<String, Flyweight>();

    public Flyweight getFlyWeight(String name) {
        Flyweight flyweight = null;

        if (pool.containsKey(name)) {
            flyweight = pool.get(name);
        }
        else {
            flyweight = new Flyweight(name);
            pool.put(name, flyweight);
        }

        return flyweight;
    }
}

// --------------------- test code

public static void main(String[] args) {
    FlyweightFactory factory = new FlyweightFactory();
    Flyweight flyweight = factory.getFlyWeight("luoxn28");

    flyweight.hello();
}