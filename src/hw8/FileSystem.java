package hw8;

// From CS 680 Notes 13
import java.util.LinkedList;

public class FileSystem {
    private static FileSystem instance = null;
    private LinkedList<Directory> rootDirs = new LinkedList<>();

    private FileSystem(){}

    public static FileSystem getFileSystem(){
        // Singleton file system - get the instance if it doesn't already exist.
        if (instance == null){
            instance = new FileSystem();
        }
        return instance;
    }

    public LinkedList<Directory> getRootDirs(){
        return this.rootDirs;
    }

    public void appendRootDir(Directory root){
        this.rootDirs.add(root);
    }

}

