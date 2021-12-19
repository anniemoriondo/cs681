package hw14;

public class StatsHandler implements Runnable{

    private AdmissionMonitor monitor;
    public StatsHandler(AdmissionMonitor admissionMonitor){
        monitor = admissionMonitor;
    }

    @Override
    public void run() { System.out.println(monitor.countCurrentVisitors()); }
}
