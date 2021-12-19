package hw14;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AdmissionMonitor {

    private int currentVisitors = 0;
    ReentrantLock lock = new ReentrantLock();
    Condition belowCapacityCondition = lock.newCondition();
    Condition visitorsPresentCondition = lock.newCondition();

    public AdmissionMonitor(){}

    public void enter(){
        lock.lock();
        try {
            while(currentVisitors > 10){ belowCapacityCondition.await(); }
            currentVisitors++;
            visitorsPresentCondition.signalAll();
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println("Interrupted");
        } finally { lock.unlock(); }
    }

    public void exit(){
        lock.lock();
        try {
            while(currentVisitors > 10){ visitorsPresentCondition.await(); }
            currentVisitors++;
            belowCapacityCondition.signalAll();
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.println("Interrupted");
        } finally { lock.unlock(); }
    }

    public int countCurrentVisitors(){
        lock.lock();
        try {return currentVisitors;}
        finally { lock.unlock(); }
    }

    public static void main(String[] args){
        AdmissionMonitor monitor = new AdmissionMonitor();

        LinkedList<Thread> entranceThreads = new LinkedList<>();
        LinkedList<Thread> exitThreads = new LinkedList<>();
        LinkedList<EntranceHandler> entrances = new LinkedList<>();
        LinkedList<ExitHandler> exits = new LinkedList<>();

        // Create and start threads for exit and entrance.
        for(int i = 0; i < 5; i++){
            EntranceHandler entrance = new EntranceHandler(monitor);
            entrances.add(entrance);
            Thread entranceThread = new Thread(entrance);
            entranceThreads.add(entranceThread);
            entranceThread.start();
            System.out.println("Visitors: " + monitor.countCurrentVisitors());

            ExitHandler exit = new ExitHandler(monitor);
            exits.add(exit);
            Thread exitThread = new Thread(exit);
            exitThreads.add(exitThread);
            exitThread.start();
            System.out.println("Visitors: " + monitor.countCurrentVisitors());
        }

        // Two-step thread termination.
        for(int i = 0; i < 5; i++){
            EntranceHandler entrance = entrances.get(i);
            entrance.setDone();
            Thread entranceThread = entranceThreads.get(i);
            entranceThread.interrupt();
            System.out.println("Visitors: " + monitor.countCurrentVisitors());

            ExitHandler exit = exits.get(i);
            exit.setDone();
            Thread exitThread = exitThreads.get(i);
            exitThread.interrupt();
            System.out.println("Visitors: " + monitor.countCurrentVisitors());
        }
    }

}
