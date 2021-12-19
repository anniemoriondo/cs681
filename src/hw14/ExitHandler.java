package hw14;

public class ExitHandler implements Runnable{

    private AdmissionMonitor monitor;
    private boolean done;

    public ExitHandler(AdmissionMonitor admissionMonitor){
        monitor = admissionMonitor;
    }
    public void setDone(){done = true;}

    @Override
    public void run() { while (true) {
        if(done){break;}
        monitor.exit();
    } }

}
