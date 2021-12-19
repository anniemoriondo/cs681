package hw15;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Observable {

    private LinkedList<Observer> observers = new LinkedList<Observer>();

    private AtomicBoolean changed;
    ReentrantLock lockObs = new ReentrantLock();

    public Observable(){}

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

        djiaWatch.addObserver((Observable obsl, Object obj) ->
            System.out.println("New DJIA: " + ((DJIAEvent)obj).getQuote())
        );

        stockWatch.addObserver((Observable obsl, Object obj) ->{
            StockEvent event = (StockEvent) obj;
            System.out.println("Current value of " + event.getTicker()
                    + ": " + event.getQuote());
        });

        // Same thing but in French
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

        // Removing the English observers
        djiaWatch.deleteObserver((Observable obsl, Object obj) ->
                System.out.println("New DJIA: " + ((DJIAEvent)obj).getQuote())
        );

        stockWatch.deleteObserver((Observable obsl, Object obj) ->{
            StockEvent event = (StockEvent) obj;
            System.out.println("Current value of " + event.getTicker()
                    + ": " + event.getQuote());
        });

        System.out.println("\nShould be just in French - still shows English?:");
        djiaWatch.changeQuote(34299.12f);
        stockWatch.changeQuote("AAPL", 878.3f);
    }

}
