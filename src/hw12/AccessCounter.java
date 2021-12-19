package hw12;
import java.util.HashMap;
import java.nio.file.Path;
import java.util.concurrent.locks.ReentrantLock;

public class AccessCounter {

    // AccessCounter as a thread-safe Singleton.

    private HashMap<Path,Integer> accessMap = new HashMap();
    private static AccessCounter instance = null;

    // Locks
    private static ReentrantLock staticLock = new ReentrantLock();
    private ReentrantLock regularLock = new ReentrantLock();

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
        regularLock.lock();
        try {
            if(accessMap.containsKey(path)){
                Integer updatedCount = accessMap.get(path) + 1;
                accessMap.replace(path, updatedCount);
            } else {
                accessMap.put(path, 1);
            }
        } finally { regularLock.unlock(); }
    }

    // Get the access count for a path.
    public int getCount(Path path){
        regularLock.lock();
        try {
            int count = 0;
            if (accessMap.containsKey(path)){
                count = accessMap.get(path);
            }
            return count;
        } finally { regularLock.unlock(); }
    }
}
