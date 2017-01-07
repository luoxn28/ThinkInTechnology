public interface IRunner {
    void run();
}

public class Runner implements IRunner {
    public void run() {
        System.out.println("Runner running...");
    }
}

public class RunnerAgent implements IRunner {
    private IRunner runner;

    public RunnerAgent(IRunner runner) {
        this.runner = runner;
    }

    public void run() {
        if (new Random().nextBoolean()) {
            System.out.println("agent安排runner跑步");
            runner.run();
        }
        else {
            System.out.println("agent不安排runner跑步");
        }
    }
}

public static void main(String[] args) {
    IRunner runner = new Runner();
    RunnerAgent agent = new RunnerAgent(runner);

    agent.run();
}


// --------------------- 动态代理

public interface Interface {
    void doSomething();
    void somethingElse(String arg);
}

public class RealObject implements Interface {
    public void doSomething() {
        System.out.println("doSomething.");
    }
    public void somethingElse(String arg) {
        System.out.println("somethingElse " + arg);
    }
}

public class DynamicProxyHandler implements InvocationHandler {
    private Object proxyed;
    
    public DynamicProxyHandler(Object proxyed) {
        this.proxyed = proxyed;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        System.out.println("代理工作了.");
        return method.invoke(proxyed, args);
    }
}

public static void main(String[] args) {
	RealObject real = new RealObject();
	Interface proxy = (Interface) Proxy.newProxyInstance(
			Interface.class.getClassLoader(), new Class[] {Interface.class},
			new DynamicProxyHandler(real));
	
	proxy.doSomething();
	proxy.somethingElse("luoxn28");
}

