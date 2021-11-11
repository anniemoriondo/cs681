package hw3;


import hw5.RunnablePrimeGenerator;

public class PrimeGenOneThread {

    public static void main(String[] args){
        // Generating primes from 1 to 2 million: One thread.
        RunnablePrimeGenerator gen = new RunnablePrimeGenerator(1L, 2000000L);
        Thread t1 = new Thread(gen);
        t1.start();
        try {t1.join();} catch (InterruptedException e){}
        gen.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));

        long numOfPrimes = gen.getPrimes().size();
        System.out.println("\n" + numOfPrimes +
                " prime numbers are found in total.");
    }
}
