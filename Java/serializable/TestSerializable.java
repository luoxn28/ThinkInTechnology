import java.io.*;

/**
 * test serializable
 */
public class TestSerializable {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        testWriteObject();
        testReadObject();
    }

    static void testReadObject() throws IOException, ClassNotFoundException {
        FileInputStream stream = new FileInputStream("H:/object.txt");
        ObjectInputStream inputStream = new ObjectInputStream(stream);
        TestSerializable.User user = null;

        user = (TestSerializable.User) inputStream.readObject();
        inputStream.close();
        stream.close();
        System.out.println(user);
    }

    static void testWriteObject() throws IOException {
        FileOutputStream stream = new FileOutputStream("H:/object.txt");
        ObjectOutputStream outputStream = new ObjectOutputStream(stream);
        TestSerializable.User user = new TestSerializable.User("jack", 23);

        outputStream.writeObject(user);
        outputStream.close();
        stream.close();

        System.out.println("write object ok");
    }

    static class User implements Serializable {
        String name;
        int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
