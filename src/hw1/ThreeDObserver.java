package hw1;

import java.util.HashMap;

public class ThreeDObserver implements Observer{

    private HashMap<String, Float> stockQuotes = new HashMap<>();
    private float djiaQuote;

    public ThreeDObserver(StockQuoteObservable stocks, DJIAQuoteObservable djia){
        this.stockQuotes = stocks.getAllQuotes();
        stocks.addObserver(this);
        this.djiaQuote = djia.getQuote();
        djia.addObserver(this);
    }

    public void update(Observable o, Object arg){
        if (arg instanceof StockEvent){
            StockEvent stockEvent = (StockEvent) arg;
            if (! stockQuotes.containsKey(stockEvent.getTicker())){
                stockQuotes.put(stockEvent.getTicker(), stockEvent.getQuote());
            } else {
                stockQuotes.replace(stockEvent.getTicker(), stockEvent.getQuote());
            }
        } else if (arg instanceof DJIAEvent){
            djiaQuote = ((DJIAEvent) arg).getQuote();
        }
    }

    public float getDjiaQuote() { return djiaQuote; }

    public float getQuote(String ticker){
        if (stockQuotes.containsKey(ticker)){
            return stockQuotes.get(ticker);
        }
        return -1f;
    }
}
