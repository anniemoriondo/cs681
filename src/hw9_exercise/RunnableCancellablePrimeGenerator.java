package hw9_exercise;

import hw5.RunnablePrimeGenerator;

import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellablePrimeGenerator extends RunnablePrimeGenerator {
    private boolean done = false;
    ReentrantLock lock = new ReentrantLock();

    public RunnableCancellablePrimeGenerator(long from, long to) {
        super(from, to);
    }

    public void setDone(){
        // Thread-safe access to done boolean.
        lock.lock();
        try { done = true;}
        finally {lock.unlock();}

    }

    public void generatePrimes(){
        lock.lock();
        try {
            for (long n = from; n <= to; n++) {
                // Stop generating prime numbers if done==true
                if(done){
                    System.out.println("Stopped generating prime numbers.");
                    this.primes.clear();
                    break;
                }
                if( isPrime(n) ){ this.primes.add(n); }
            }
        } finally { lock.unlock(); }

    }

    public static void main(String[] args) {
        System.out.println("Cancellation test:");
        RunnableCancellablePrimeGenerator gen = new RunnableCancellablePrimeGenerator(1,100);
        RunnableCancellablePrimeGenerator gen2 = new RunnableCancellablePrimeGenerator(1, 1000);
        Thread thread = new Thread(gen);
        Thread thread2 = new Thread(gen2);
        thread.start();
        thread2.start();
        gen.setDone();
        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gen.getPrimes().forEach( (Long prime)-> System.out.print(prime + ", ") );
        System.out.println("\n" + gen.getPrimes().size() + " prime numbers are found from 1 to 100.");
        gen2.getPrimes().forEach( (Long prime)-> System.out.print(prime + ", ") );
        System.out.println("\n" + gen2.getPrimes().size() + " prime numbers are found from 1 to 1000.");
    }
}
