package hw15;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Observable {

    private LinkedList<Observer> observers = new LinkedList<Observer>();

    private AtomicBoolean changed = new AtomicBoolean();
    ReentrantLock lockObs = new ReentrantLock();

    public Observable(){ changed.set(false); }

    // Add an observer
    public void addObserver(Observer o){
        lockObs.lock();
        try { observers.add(o);}
        finally { lockObs.unlock(); }
    }

    // Delete an observer
    public void deleteObserver(Observer o){
        lockObs.lock();
        try { observers.remove(o);}
        finally { lockObs.unlock(); }
    }

    // Delete an observer by index
    public void deleteObserver(int i){
        lockObs.lock();
        try {
            if (i < observers.size()){
                observers.remove(observers.get(i));}
            }
        finally { lockObs.unlock(); }
    }

    // Mark that the observable has changed
    public void setChanged(){ changed.set(true); }

    // Reset changed status
    public void clearChanged(){ changed.set(false); }

    // Indicates whether this hw1.Observable has changed
    public boolean hasChanged(){ return this.changed.get(); }

    // Notifies all observers
    public void notifyObservers(Object obj){
        Object[] arrLocal;
        lockObs.lock();
        try {
            if (! this.hasChanged()){return;}
            arrLocal = observers.toArray();
        } finally { lockObs.unlock(); }

        // Open call
        for (int i = arrLocal.length - 1; i >= 0; i--){
            ((Observer)arrLocal[i]).update(this, obj);
        }

    }

    public static void main(String[] args){
        DJIAQuoteObservable djiaWatch = new DJIAQuoteObservable();

        String[] stocks = {"AAPL", "GOOG"};
        float[] quotes = { 903.5f, 2033.2f};
        StockQuoteObservable stockWatch = new StockQuoteObservable(stocks, quotes);

        // Thread that adds observers
        Thread t1 = new Thread(() -> {
            djiaWatch.addObserver((Observable obsl, Object obj) ->
                    System.out.println("New DJIA: " + ((DJIAEvent)obj).getQuote())
            );
            stockWatch.addObserver((Observable obsl, Object obj) ->{
                StockEvent event = (StockEvent) obj;
                System.out.println("Current value of " + event.getTicker()
                        + ": " + event.getQuote());
            });
        });

        // Another thread that adds observers and changes the quotes
        Thread t2 = new Thread(() ->{
            djiaWatch.addObserver((Observable obsl, Object obj) ->
                    System.out.println("Nouvelle DJIA: " + ((DJIAEvent)obj).getQuote())
            );

            stockWatch.addObserver((Observable obsl, Object obj) ->{
                StockEvent event = (StockEvent) obj;
                System.out.println("Valeur de " + event.getTicker()
                        + ": " + event.getQuote());
            });

            djiaWatch.changeQuote(34307.84f);
            stockWatch.changeQuote("GOOG", 1981.0f);
        });

        // Remove first observer of each observable
        Thread t3 = new Thread(() -> {
            djiaWatch.deleteObserver(0);
            stockWatch.deleteObserver(0);
        });


        t1.start();
        t2.start();
        t3.start();
    }

}
