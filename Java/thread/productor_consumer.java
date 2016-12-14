import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * product and customer
 */
public class Hello {
    public static void main(String[] args) throws InterruptedException {
        BlockingList list = new BlockingList(10);
        ExecutorService executors = Executors.newCachedThreadPool();

        executors.execute(new Productor(list));
        executors.execute(new Productor(list));
        executors.execute(new Customer(list));
        executors.execute(new Customer(list));
    }
}

class Productor implements Runnable {
    private BlockingList list;
    private static int i = 0;

    public Productor(BlockingList list) {
        this.list = list;
    }

    public void run() {
        while (true) {
            list.add("hello world " + i++);
            System.out.println("-----------------add data ok");
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Customer implements Runnable {
    private BlockingList list;

    public Customer(BlockingList list) {
        this.list = list;
    }

    public void run() {
        while (true) {
            System.out.println(list.get());
        }
    }
}

class BlockingList {
    private List<String> list = new ArrayList<String>();
    private int size = 0;

    private Lock lock = new ReentrantLock();
    private Condition notFull  = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public BlockingList(int size) {
        this.size = size;
    }

    public void add(String data) {
        lock.lock();
        try {
            while (list.size() >= size) {
                notFull.await();
            }

            list.add(data);
            notEmpty.signal();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    public String get() {
        String data = "";

        lock.lock();
        try {
            while (list.size() == 0) {
                notEmpty.await();
            }

            data = list.remove(0);
            notFull.signal();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }

        return data;
    }
}