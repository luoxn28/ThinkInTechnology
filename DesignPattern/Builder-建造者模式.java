/**
 * Builder模式
 */
public class User {
    private String name;
    private int age;
    private String phone;
    private String address;

    public static class Builder {
        private String name;
        private int age;
        private String phone;
        private String address;

        public Builder(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

// ----------- 以下是测试类
public static void main(String[] args) {
    User user = new User.Builder("luoxn28", 23).phone("119").build();
	
    System.out.print(user);
}