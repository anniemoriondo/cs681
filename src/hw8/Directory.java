package hw8;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class Directory extends FSElement {
    private LinkedList<FSElement> children = new LinkedList<>();

    public Directory(Directory parent, String name, LocalDateTime creationTime){
        // File size of directory is always 0
        super(parent, name, 0, creationTime);
        // Thread safe access to LinkedList
        lock.lock();
        try {
            if (parent != null){ parent.appendChild(this); }
        } finally { lock.unlock(); }
    }

    public LinkedList<FSElement> getChildren(){
        // Thread safe access to LinkedList
        lock.lock();
        try { return this.children; } finally { lock.unlock(); }
    }

    public void appendChild(FSElement newChild){
        // Thread safe access to LinkedList
        lock.lock();
        try {this.children.add(newChild);} finally { lock.unlock(); }
    }

    public int countChildren(){
        // Thread safe access to LinkedList
        lock.lock();
        try { return this.children.size();} finally { lock.unlock(); }
    }

    // Already atomic
    public boolean isDirectory() { return true; }

    // Already atomic
    public boolean isLink() { return false; }

    /**
     * Gets all directories immediately inside this Directory
     * @return LinkedList of all children which are Directories
     */
    public LinkedList<Directory> getSubDirectories(){
        // Thread safe access to LinkedList
        lock.lock();
        try {
            LinkedList<Directory> subDirectories = new LinkedList<>();
            for (FSElement thisElem : this.children){
                if (thisElem.isDirectory()){
                    subDirectories.add((Directory) thisElem);
                }
            }
            return subDirectories;
        } finally { lock.unlock(); }
    }

    /**
     * Gets all files immediately inside this Directory
     * @return LinkedList of all children which are Files
     */
    public LinkedList<File> getFiles(){
        // Thread safe access to LinkedList
        lock.lock();
        try {
            LinkedList<File> files = new LinkedList<>();
            for (FSElement thisElem : this.children){
                if (!thisElem.isDirectory() && !thisElem.isLink()){
                    files.add((File) thisElem);
                }
            }
            return files;
        } finally { lock.unlock(); }
    }

    /**
     * Gets all links immediately inside this Directory
     * @return LinkedList of all children which are Links
     */
    public LinkedList<Link> getLinks(){
        // Thread safe access to LinkedList
        lock.lock();
        try {
            LinkedList<Link> links = new LinkedList<>();
            for (FSElement thisElem : this.children){
                if (thisElem.isLink()){
                    links.add((Link) thisElem);
                }
            }
            return links;
        } finally { lock.unlock(); }
    }

    /**
     * Recursively gets size of directory and all subdirectories/their files.
     * @return int representing total directory size
     */
    public int getTotalSize(){
        // Thread safe access to LinkedList
        lock.lock();
        try {
            int totalSize = 0;
            for (FSElement thisChild : this.getChildren()){
                if (thisChild.isDirectory()){
                    totalSize += ((Directory)thisChild).getTotalSize();
                } else {
                    totalSize += thisChild.getSize();
                }
            }
            return totalSize;
        } finally { lock.unlock(); }
    }

    public static void main(String[] args){
        Directory myDir = new Directory(null, "Annie", LocalDateTime.now());
        System.out.println(myDir.getName());
        System.out.println(myDir.isLink());
    }
}
