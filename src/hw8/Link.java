package hw8;

import java.time.LocalDateTime;

public class Link extends FSElement {
    private FSElement target;

    public Link (Directory parent, String name, LocalDateTime creationTime,
                 FSElement target){
        // File size of link is always 0
        super(parent, name, 0, creationTime);
        // Thready safe access to LinkedList
        lock.lock();
        try {
            this.target = target;
            if (parent != null){ parent.appendChild(this); }
        } finally {
            lock.unlock();
        }
    }

    // Already atomic
    public boolean isDirectory() { return false; }

    // Already atomic
    public boolean isLink() { return true; }

    public FSElement getTarget(){ return target; }

    // Info about target
    public String targetName(){
        // Thread-safe access
        lock.lock();
        try{return target.getName(); } finally { lock.unlock(); }
    }

    public String targetLocation(){
        // Thread-safe access
        lock.lock();
        try {
        return target.getParent() != null ?
                target.getParent().getName() : null;
        } finally { lock.unlock(); }
    }

    public int targetSize(){
        // Returns target's total size if it's a directory; else return its size
        // Thread-safe access
        lock.lock();
        try {
            return target.isDirectory() ?
                ((Directory)target).getTotalSize() : target.getSize();
        } finally { lock.unlock(); }
    }

    public boolean targetIsDirectory(){
        // Thread-safe access
        lock.lock();
        try {return target.isDirectory(); } finally { lock.unlock(); }
    }

    public boolean targetIsLink(){
        // Thread-safe access
        lock.lock();
        try {return target.isLink(); } finally { lock.unlock(); }}


}
