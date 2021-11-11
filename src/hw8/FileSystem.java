package hw8;

// From CS 680 Notes 13
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class FileSystem implements Runnable {
    private static FileSystem instance = null;
    private static ReentrantLock lock = new ReentrantLock();
    private LinkedList<Directory> rootDirs = new LinkedList<>();

    private FileSystem(){}

    public void run(){}

    public static FileSystem getFileSystem(){
        lock.lock();
        try {
            // Singleton file system - get the instance if it doesn't already exist.
            if (instance == null){
                instance = new FileSystem();
            }
            return instance;
        } finally {
            lock.unlock();
        }
    }

    public LinkedList<Directory> getRootDirs(){
        // Thread-safe access to rootDirs
        lock.lock();
        try{return this.rootDirs;} finally { lock.unlock(); }

    }

    public void appendRootDir(Directory root){
        // Thread-safe access to rootDirs
        lock.lock();
        try { this.rootDirs.add(root); } finally { lock.unlock();
        }
    }

    public static void main(String[] args){
        // Verify concurrent singleton
        FileSystem fs1 = new FileSystem();
        FileSystem fs2 = new FileSystem();

        // Multiple threads should all be accessing same instance
        System.out.println("Same instance?");
        Thread t1 = new Thread(fs1);
        Thread t2 = new Thread(fs2);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();

            System.out.println(fs1.getFileSystem());
            System.out.println(fs2.getFileSystem());
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }



}

