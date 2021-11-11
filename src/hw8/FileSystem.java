package hw8;

// From CS 680 Notes 13
import java.time.LocalDateTime;
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
        // Tests adapted from CS680 HW08 test classes.

        // Verify concurrent singleton
        FileSystem fs1 = SampleFileSystem.createFS();
        FileSystem fs2 = SampleFileSystem.createFS();
        FileSystem fs3 = SampleFileSystem.createFS();


        Thread t1 = new Thread(fs1);
        Thread t2 = new Thread(fs2);
        Thread t3 = new Thread(fs2);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();

            // Multiple threads should all be accessing same instance
            System.out.println("Same instance?");
            System.out.println(fs1.getFileSystem());
            System.out.println(fs2.getFileSystem());
            System.out.println(fs3.getFileSystem());

            System.out.println("\nSame root dir?");
            System.out.println(fs1.getRootDirs().getFirst().getName());
            System.out.println(fs2.getRootDirs().getFirst().getName());

            // Checking to make sure changes are reflected on both threads
            Directory home1 = (Directory) fs1.getRootDirs().getFirst()
                    .getChildren().get(1);
            Directory home2 = (Directory) fs2.getRootDirs().getFirst()
                    .getChildren().get(1);

            System.out.println("Home 1:");
            for (FSElement childElem : home1.getChildren()){
                System.out.println(childElem.getName());
            }
            System.out.println(home1.countChildren());

            System.out.println("\nHome 2:");

            for (FSElement childElem : home2.getChildren()){
                System.out.println(childElem.getName());
            }
            System.out.println(home2.countChildren());

            System.out.println("\nAdding dir on FS 1.");
            Directory budget =
                    new Directory(home1, "Budget", LocalDateTime.now());

            System.out.println("Home 1:");
            for (FSElement childElem : home1.getChildren()){
                System.out.println(childElem.getName());
            }
            System.out.println(home1.countChildren());

            System.out.println("\nHome 2:");
            for (FSElement childElem : home2.getChildren()){
                System.out.println(childElem.getName());
            }
            System.out.println(home2.countChildren());

            System.out.println("\nHome1 Size: " + home1.getTotalSize());
            System.out.println("Home2 Size: " + home2.getTotalSize());

            System.out.println("\nAdd a file of size 200");
            File budget2022 = new File(budget, "2022 Budget", 200,
                    LocalDateTime.now());
            System.out.println("Home1 Size: " + home1.getTotalSize());
            System.out.println("Home2 Size: " + home2.getTotalSize());

            Link goToBudget = new Link(home1.getSubDirectories().getFirst(),
                    "Go to Budget", LocalDateTime.now(), budget);

            // Verify that directory link contents match
            System.out.println("\nLinks (Home 1 first subdir):");
            for(Link thisLink :
                    home1.getSubDirectories().getFirst().getLinks()){
                System.out.println(thisLink.getName());
            }

            System.out.println("Links (Home 2 first subdir):");
            for(Link thisLink :
                    home2.getSubDirectories().getFirst().getLinks()){
                System.out.println(thisLink.getName());
            }


        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }



}

