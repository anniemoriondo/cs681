package hw1;

import hw1.Observable;

public interface Observer {

    public abstract void update(Observable obs, Object obj);
}
