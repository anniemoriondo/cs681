package hw10;

import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentSingleton implements Runnable{
    // Revising ConcurrentSingleton to use an AtomicReference instead of a lock

    private ConcurrentSingleton(){}

    // Sole instance of ConcurrentSingleton class - as an AtomicReference
    private static AtomicReference<ConcurrentSingleton> instance =
            new AtomicReference<>();

    public void run(){}

    // Static factory method
    public static AtomicReference<ConcurrentSingleton> getInstance(){
            // Make an instance if there isn't already one
            instance.compareAndSet(null, new ConcurrentSingleton());
            return instance;

    }

    public static void main(String[] args){
        for (int i = 0; i < 10; i++){
            new Thread(()->{
                System.out.println(ConcurrentSingleton.getInstance());
            }).start();
        }
    }
}
