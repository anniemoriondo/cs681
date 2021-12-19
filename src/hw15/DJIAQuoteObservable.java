package hw15;

public class DJIAQuoteObservable extends Observable {

    // DJIA as of Friday May 21, 2021, 10:30 PM
    private float quote = 34207.84f;

    public DJIAQuoteObservable(){}

    public DJIAQuoteObservable(float currentQuote){
        this.quote = currentQuote;
    }

    public float getQuote() { return quote; }

    public void changeQuote(float quote){
        this.quote = quote;
        setChanged();
        notifyObservers(new DJIAEvent(quote));
    }
}
