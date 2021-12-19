package hw12;
import java.nio.file.Path;
import java.util.Random;

public class RequestHandler implements Runnable{

    private AccessCounter counter;
    private Random rng = new Random();

    public RequestHandler(){ counter = AccessCounter.getInstance();}

    @Override
    public void run() {
        while(true){
            try {
                int fileIndex = rng.nextInt(7);
                String fileLocation = "./file" + fileIndex + ".txt";
                Path filePath = Path.of(fileLocation);
                counter.increment(filePath);
                counter.getCount(filePath);
                Thread.sleep(3000);
            } catch (InterruptedException e){e.printStackTrace();}
        }
    }



    public static void main(String[] args){

    }
}
