package hw12;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Random;

public class RequestHandler implements Runnable{

    private AccessCounter counter;
    private Random rng = new Random();

    // Volatile flag.
    volatile boolean done = false;

    public RequestHandler(){ counter = AccessCounter.getInstance();}

    public void setDone(){ done = true;}

    @Override
    public void run() {
        //System.out.print("Running: ");
        //System.out.println(this);
        while(true){
            if(done){ break; }
            try {
                int fileIndex = rng.nextInt(7);
                String fileLocation = "./file" + fileIndex + ".txt";
                Path filePath = Path.of(fileLocation);
                counter.increment(filePath);
                counter.getCount(filePath);
                Thread.sleep(3000);
            } catch (InterruptedException e){
                //e.printStackTrace();
                System.out.println("Interrupted.");
            }
        }
    }



    public static void main(String[] args){
        LinkedList<RequestHandler> handlers = new LinkedList<>();
        LinkedList<Thread> threads = new LinkedList<>();
        for (int i = 0; i < 10; i++){
            RequestHandler handler = new RequestHandler();
            handlers.add(handler);
            Thread handlerThread = new Thread(handler);
            threads.add(handlerThread);
            handlerThread.start();
        }
        for (int i = 0; i < 10; i++){
            handlers.get(i).setDone();
            threads.get(i).interrupt();
        }
        System.out.println("Access counts:");
        AccessCounter counter = AccessCounter.getInstance();
        for (int fileIndex = 1; fileIndex < 8; fileIndex++){
            Path filePath = Path.of("./file" + fileIndex + ".txt");
            System.out.println(filePath + ": " + counter.getCount(filePath));
        }
    }
}
