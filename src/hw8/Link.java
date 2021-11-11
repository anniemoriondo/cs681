package hw8;

import java.time.LocalDateTime;

public class Link extends FSElement {
    private FSElement target;

    public Link (Directory parent, String name, LocalDateTime creationTime,
                 FSElement target){
        // File size of link is always 0
        super(parent, name, 0, creationTime);
        this.target = target;
        if (parent != null){ parent.appendChild(this); }
    }

    public boolean isDirectory() { return false; }

    public boolean isLink() { return true; }

    public FSElement getTarget(){ return target; }

    // Info about target
    public String targetName(){ return target.getName(); }

    public String targetLocation(){
        return target.getParent() != null ?
                target.getParent().getName() : null;
    }

    public int targetSize(){
        // Returns target's total size if it's a directory; else return its size
        return target.isDirectory() ?
                ((Directory)target).getTotalSize() : target.getSize();
    }

    public boolean targetIsDirectory(){ return target.isDirectory(); }

    public boolean targetIsLink(){return target.isLink();}


}
