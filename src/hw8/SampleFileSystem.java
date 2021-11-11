package hw8;

import java.time.LocalDateTime;

public class SampleFileSystem {

    public static FileSystem createFS(){
        FileSystem fs = FileSystem.getFileSystem();

        // If the directory is not already set up, set it up.
        if (fs.getRootDirs().size() == 0){
            fs.appendRootDir(new Directory(null, "root", LocalDateTime.now()));

            Directory root = fs.getRootDirs().getFirst();
            Directory applications = new Directory(root, "applications",
                    LocalDateTime.now());
            Directory home = new Directory(root, "home", LocalDateTime.now());
            Directory code = new Directory(home, "code", LocalDateTime.now());
            Directory pics = new Directory(home, "pics", LocalDateTime.now());

            File a = new File(applications, "a", 100, LocalDateTime.now());
            File b = new File(home, "b", 200, LocalDateTime.now());
            File c = new File(code, "c", 300, LocalDateTime.now());
            File d = new File(code, "d", 400, LocalDateTime.now());
            File e = new File(pics, "e", 500, LocalDateTime.now());
            File f = new File(pics, "f", 600, LocalDateTime.now());

            Link x = new Link(home, "x", LocalDateTime.now(), applications);
            Link y = new Link(code, "y", LocalDateTime.now(), a);
        }

        return fs;
    }
}
