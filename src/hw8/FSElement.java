package hw8;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;


public class FSElement {
    ReentrantLock lock = new ReentrantLock();
    private String name;
    private int size;
    private LocalDateTime creationTime;
    private Directory parent;

    public FSElement(Directory parent, String name, int size,
                     LocalDateTime creationTime){
        // Thread safe initialization of all data fields.
        lock.lock();
        try{
            this.parent = parent;
            this.name = name;
            this.size = size;
            this.creationTime = creationTime;
        } finally { lock.unlock(); }
    }

    // Getters
    public Directory getParent(){
        // Thread-safe access to data field parent.
        lock.lock();
        try{ return this.parent; }
        finally { lock.unlock(); }
    }

    public String getName(){
        // Thread-safe access to data field name.
        lock.lock();
        try { return this.name; }
        finally { lock.unlock(); }
    }

    public LocalDateTime getCreationTime(){
        // Thread-safe access to data field creationTime.
        lock.lock();
        try { return this.creationTime; }
        finally { lock.unlock(); }
    }

    public int getSize(){
        // Thread-safe access to data field size.
        lock.lock();
        try { return this.size; }
        finally { lock.unlock(); }
    }

    // Setters
    public void setParent(Directory newParent){
        // Thread-safe access to data field parent.
        lock.lock();
        try{ this.parent = newParent; }
        finally { lock.unlock(); }
    }

    public void setName(String newName){ this.name = newName; }

    public void setCreationTime(LocalDateTime newTime){
        // Thread-safe access to data field creationTime.
        lock.lock();
        try { this.creationTime = newTime; }
        finally{ lock.unlock(); }
    }

    public void setSize(int newSize){
        // Thread-safe access to data field size.
        lock.lock();
        try{this.size = newSize;}
        finally{lock.unlock();}
    }

    // Overridden by directory subclasses. Already atomic.
    public boolean isDirectory(){return false;}

    // Overidden by link subclasses. Already atomic.
    public boolean isLink(){return true;}

}

