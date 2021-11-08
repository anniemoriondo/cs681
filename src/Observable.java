import java.util.LinkedList;

public abstract class Observable {

    public LinkedList<Observer> observers;

    public Observable(){
        observers = new LinkedList<Observer>();
    }


}
