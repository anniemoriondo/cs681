package hw15;

import java.util.HashMap;

public class StockQuoteObservable extends Observable {

    private HashMap <String, Float> stockQuotes = new HashMap<>();

    public StockQuoteObservable(String[] tickers, float[] quotes){
        // Must be the same length
        if (tickers.length != quotes.length){ return; }
        for (int i = 0; i < tickers.length; i++){
            stockQuotes.put(tickers[i], quotes[i]);
        }
    }

    public HashMap<String, Float> getAllQuotes(){
        return this.stockQuotes;
    }

    public void changeQuote(String ticker, float quote){
        if (!stockQuotes.containsKey(ticker)){
            stockQuotes.put(ticker, quote);
        } else {
            stockQuotes.replace(ticker, quote);
        }
        setChanged();
        notifyObservers(new StockEvent(ticker, quote));
    }

    public static void main(String[] args){

    }
}
