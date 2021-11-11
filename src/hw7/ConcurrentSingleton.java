package hw7;

import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentSingleton {

    private ConcurrentSingleton(){}

    // Sole instance of ConcurrentSingleton class
    private static ConcurrentSingleton instance = null;

    private static ReentrantLock lock = new ReentrantLock();

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

    }


}
