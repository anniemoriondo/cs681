package hw6;

import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellablePrimeFactorizer extends RunnablePrimeFactorizer{

    private boolean done = false;
    ReentrantLock lock = new ReentrantLock();

    public RunnableCancellablePrimeFactorizer(long dividend){
        super(dividend);
    }

    public void setDone(){
        // Thread-safe access to done boolean.
        lock.lock();
        try{ done = true; }
        finally {
            lock.unlock();
        }
    }

    public void generatePrimeFactors(){
        // Lock for thread safety
        lock.lock();
        try {
            long divisor = 2;
            while( dividend != 1 && divisor <= to ){
                // Stop if done is true
                if(done){
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
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args){
        System.out.println("\nRunnableCancellablePrimeFactorizer:");
        // Dividends
        long test1 = 24;
        long test2 = 37;
        long test3 = 54321;
        long test4 = 2021;
        long test5 = 92387;
        long test6 = 2000000-1;

        // Class under test
        RunnableCancellablePrimeFactorizer rcpf1 =
                new RunnableCancellablePrimeFactorizer(test1);
        RunnableCancellablePrimeFactorizer rcpf2 =
                new RunnableCancellablePrimeFactorizer(test2);
        RunnableCancellablePrimeFactorizer rcpf3 =
                new RunnableCancellablePrimeFactorizer(test3);
        RunnableCancellablePrimeFactorizer rcpf4 =
                new RunnableCancellablePrimeFactorizer(test4);
        RunnableCancellablePrimeFactorizer rcpf5 =
                new RunnableCancellablePrimeFactorizer(test5);
        RunnableCancellablePrimeFactorizer rcpf6 =
                new RunnableCancellablePrimeFactorizer(test6);

        // Threads
        Thread t1 = new Thread(rcpf1);
        Thread t2 = new Thread(rcpf2);
        Thread t3 = new Thread(rcpf3);
        Thread t4 = new Thread(rcpf4);
        Thread t5 = new Thread(rcpf5);
        Thread t6 = new Thread(rcpf6);

        // Start threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();

        // Try joining the threads.
        try{
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        // Results
        System.out.println("Prime factors of " + test1 + ": " +
                rcpf1.getPrimeFactors());
        System.out.println("Prime factors of " + test2 + ": " +
                rcpf2.getPrimeFactors());
        System.out.println("Prime factors of " + test3 + ": " +
                rcpf3.getPrimeFactors());
        System.out.println("Prime factors of " + test4 + ": " +
                rcpf4.getPrimeFactors());
        System.out.println("Prime factors of " + test5 + ": " +
                rcpf5.getPrimeFactors());
        System.out.println("Prime factors of " + test6 + ": " +
                rcpf6.getPrimeFactors());

        System.out.println("Cancellation test:");

        // Dividends
        long test7 = 125;
        long test8 = 1023;

        // Classes under test
        RunnableCancellablePrimeFactorizer rcpf7 =
                new RunnableCancellablePrimeFactorizer(test7);
        RunnableCancellablePrimeFactorizer rcpf8 =
                new RunnableCancellablePrimeFactorizer(test8);

        // Threads
        Thread t7 = new Thread(rcpf7);
        Thread t8 = new Thread(rcpf8);

        t7.start();
        t8.start();
        rcpf7.setDone();

        try {
            t7.join();
            t8.join();
        } catch (InterruptedException e){e.printStackTrace();}

        System.out.println("Prime factors of " + test7 + ": " +
                rcpf7.getPrimeFactors());
        System.out.println("Prime factors of " + test8 + ": " +
                rcpf8.getPrimeFactors());
    }
}
