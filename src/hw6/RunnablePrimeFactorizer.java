package hw6;

import hw5.RunnablePrimeGenerator;

public class RunnablePrimeFactorizer extends PrimeFactorizer
        implements Runnable {

    public RunnablePrimeFactorizer(long dividend){
        super(dividend);
    }

    // Generates prime factors when thread is run
    public void run(){ generatePrimeFactors(); }

    public static void main(String[] args){
        // Dividends
        long test1 = 24;
        long test2 = 37;
        long test3 = 54321;
        long test4 = 2021;
        long test5 = 92387;
        long test6 = 2000000-1;

        RunnablePrimeFactorizer rpf1 = new RunnablePrimeFactorizer(test1);
        RunnablePrimeFactorizer rpf2 = new RunnablePrimeFactorizer(test2);
        RunnablePrimeFactorizer rpf3 = new RunnablePrimeFactorizer(test3);
        RunnablePrimeFactorizer rpf4 = new RunnablePrimeFactorizer(test4);
        RunnablePrimeFactorizer rpf5 = new RunnablePrimeFactorizer(test5);
        RunnablePrimeFactorizer rpf6 = new RunnablePrimeFactorizer(test6);

        // Each RunnablePrimeFactorizer gets its own thread.
        Thread t1 = new Thread(rpf1);
        Thread t2 = new Thread(rpf2);
        Thread t3 = new Thread(rpf3);
        Thread t4 = new Thread(rpf4);
        Thread t5 = new Thread(rpf5);
        Thread t6 = new Thread(rpf6);

        // Start threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();

        // Join threads
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
        } catch (InterruptedException e){ System.out.println(e);}

        // Results
        System.out.println("Prime factors of " + test1 + ": " +
                rpf1.getPrimeFactors());
        System.out.println("Prime factors of " + test2 + ": " +
                rpf2.getPrimeFactors());
        System.out.println("Prime factors of " + test3 + ": " +
                rpf3.getPrimeFactors());
        System.out.println("Prime factors of " + test4 + ": " +
                rpf4.getPrimeFactors());
        System.out.println("Prime factors of " + test5 + ": " +
                rpf5.getPrimeFactors());
        System.out.println("Prime factors of " + test6 + ": " +
                rpf6.getPrimeFactors());
    }
}
