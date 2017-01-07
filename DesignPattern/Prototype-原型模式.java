public class Thing implements Cloneable {
    public Thing() {
        System.out.println("constructor thing");
    }

    @Override
    public Thing clone() throws CloneNotSupportedException {
        Thing thing = null;

        thing = (Thing) super.clone();
        return thing;
    }
}

// --------------------- test code

public static void main(String[] args) throws CloneNotSupportedException {
    Thing thing = new Thing();
    Thing cloneThing = thing.clone();
}
