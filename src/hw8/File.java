package hw8;

import java.time.LocalDateTime;

public class File extends FSElement {

    public File (Directory parent, String name, int size,
                 LocalDateTime creationTime){
        // Parent constructor is thread safe
        super(parent, name, size, creationTime);
        // Accessing a LinkedList must be done in a thread safe way
        lock.lock();
        try{parent.appendChild(this);} finally { lock.unlock();
        }

    }

    // Already atomic
    public boolean isDirectory() { return false; }

    // Already atomic
    public boolean isLink() { return false; }
}

