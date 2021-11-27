package hw9;

import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellableInterruptiblePrimeFactorizer
    extends RunnableCancellablePrimeFactorizer {

    private boolean done = false;
    private final ReentrantLock lock = new ReentrantLock();

    public RunnableCancellableInterruptiblePrimeFactorizer(long dividend){
        super(dividend);
    }

    public void setDone(){
        lock.lock();
        try { done = true;} finally { lock.unlock(); }
    }

    @Override
    public void generatePrimeFactors() {
        // Lock for thread safety


            long divisor = 2;
            while (dividend != 1 && divisor <= to ){
                // Lock the thread
                lock.lock();
                // Check whether done and stop if so
                try {
                    // Must stop if done
                    if (done){
                        System.out.println("Stopped generating prime factors.");
                        this.factors.clear();
                        break;
                    }
                    // If you find a prime factor, add it
                    if(dividend % divisor == 0) {
                        factors.add(divisor);
                        dividend /= divisor;
                        // Otherwise move on
                    }else {
                        if(divisor==2){ divisor++; }
                        else{ divisor += 2; }
                    }
                } finally { lock.unlock(); }
                // Sleep and check for interruptions
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    System.out.println(e.toString());
                    continue;
                }
            }
    }

    public static void main(String[] args){

    }
}
