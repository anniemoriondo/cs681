package hw16;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class AccessCounter {

    // AccessCounter as a thread-safe Singleton.

    private ConcurrentHashMap<Path, AtomicInteger> accessMap = new ConcurrentHashMap<>();
    private static AccessCounter instance = null;

    // Locks
    private static ReentrantLock staticLock = new ReentrantLock();

    private AccessCounter(){}

    public static AccessCounter getInstance() {
        staticLock.lock();
        try {
            if(instance == null){ instance = new AccessCounter(); }
            return instance;
        } finally { staticLock.unlock(); }
    }

    // Increment the access count for a path.
    public void increment(Path path){
        accessMap.putIfAbsent(path, new AtomicInteger(0));
        accessMap.get(path).incrementAndGet();
    }

    // Get the access count for a path.
    public int getCount(Path path){
        return accessMap.compute(path, (Path k, AtomicInteger v) ->{
            // If it isn't present, it's been accessed 0 times.
            return v == null? new AtomicInteger(0) : v;
        }).intValue();
    }
}
