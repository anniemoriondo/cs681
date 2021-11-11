package hw8;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class Directory extends FSElement {
    private LinkedList<FSElement> children = new LinkedList<>();

    public Directory(Directory parent, String name, LocalDateTime creationTime){
        // File size of directory is always 0
        super(parent, name, 0, creationTime);
        if (parent != null){ parent.appendChild(this); }
    }

    public LinkedList<FSElement> getChildren(){
        return this.children;
    }

    public void appendChild(FSElement newChild){
        this.children.add(newChild);
    }

    public int countChildren(){
        return this.children.size();
    }

    public boolean isDirectory() { return true; }

    public boolean isLink() { return false; }

    /**
     * Gets all directories immediately inside this Directory
     * @return LinkedList of all children which are Directories
     */
    public LinkedList<Directory> getSubDirectories(){
        LinkedList<Directory> subDirectories = new LinkedList<>();
        for (FSElement thisElem : this.children){
            if (thisElem.isDirectory()){
                subDirectories.add((Directory) thisElem);
            }
        }
        return subDirectories;
    }

    /**
     * Gets all files immediately inside this Directory
     * @return LinkedList of all children which are Files
     */
    public LinkedList<File> getFiles(){
        LinkedList<File> files = new LinkedList<>();
        for (FSElement thisElem : this.children){
            if (!thisElem.isDirectory() && !thisElem.isLink()){
                files.add((File) thisElem);
            }
        }
        return files;
    }

    /**
     * Gets all links immediately inside this Directory
     * @return LinkedList of all children which are Links
     */
    public LinkedList<Link> getLinks(){
        LinkedList<Link> links = new LinkedList<>();
        for (FSElement thisElem : this.children){
            if (thisElem.isLink()){
                links.add((Link) thisElem);
            }
        }
        return links;
    }

    /**
     * Recursively gets size of directory and all subdirectories/their files.
     * @return int representing total directory size
     */
    public int getTotalSize(){
        int totalSize = 0;
        for (FSElement thisChild : this.getChildren()){
            if (thisChild.isDirectory()){
                totalSize += ((Directory)thisChild).getTotalSize();
            } else {
                totalSize += thisChild.getSize();
            }
        }
        return totalSize;
    }

    public static void main(String[] args){
        Directory myDir = new Directory(null, "Annie", LocalDateTime.now());
        System.out.println(myDir.getName());
        System.out.println(myDir.isLink());
    }
}
