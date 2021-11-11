package hw7;

import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentSingleton implements Runnable{

    private ConcurrentSingleton(){}

    // Sole instance of ConcurrentSingleton class
    private static ConcurrentSingleton instance = null;

    private static ReentrantLock lock = new ReentrantLock();

    public void run(){
        System.out.println(getInstance());
    }

    // Static factory method
    public static ConcurrentSingleton getInstance(){
        lock.lock();
        try {
            // Make an instance if there isn't already one
            if (instance == null){ instance = new ConcurrentSingleton(); }
            return instance;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args){
        ConcurrentSingleton cs1 = new ConcurrentSingleton();
        ConcurrentSingleton cs2 = new ConcurrentSingleton();
        ConcurrentSingleton cs3 = new ConcurrentSingleton();

        System.out.println(
                "All three threads should be accessing the same instance.");

        // Multiple threads
        Thread t1 = new Thread(cs1);
        Thread t2 = new Thread(cs2);
        Thread t3 = new Thread(cs3);

        // Run multiple threads
        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e){e.printStackTrace();}

    }
}
