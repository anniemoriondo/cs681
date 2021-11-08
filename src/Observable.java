import java.util.LinkedList;

public abstract class Observable {

    private LinkedList<Observer> observers = new LinkedList<Observer>();

    private boolean changed;

    public Observable(){}

    // Add an observer
    public void addObserver(Observer o){ observers.add(o);}

    // Delete an observer
    public void deleteObserver(Observer o){ observers.remove(o);}

    // Mark that the observable has changed
    public void setChanged(){ changed = true; }

    // Reset changed status
    public void clearChanged(){ changed = false; }

    // Indicates whether this Observable has changed
    public boolean hasChanged(){ return this.changed; }

    // Notifies all observers
    public void notifyObservers(Object obj){
        if (this.hasChanged()){

        }
    }

    // Put the client code in main - StockQuoteObservable etc.

    // What _is_ an abstract class?

}
