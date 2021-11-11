package hw8;

// From CS 680 Notes 13
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class FileSystem {
    private static FileSystem instance = null;
    private static ReentrantLock lock = new ReentrantLock();
    private LinkedList<Directory> rootDirs = new LinkedList<>();

    private FileSystem(){}

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
        return this.rootDirs;
    }

    public void appendRootDir(Directory root){
        this.rootDirs.add(root);
    }

}

