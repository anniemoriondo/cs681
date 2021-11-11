package hw5;


public class PrimeGenOneThread {

    public static void main(String[] args){
        // Generating primes from 1 to 2 million: One thread.
        RunnableCancellablePrimeGenerator gen =
                new RunnableCancellablePrimeGenerator(1L, 2000000L);
        Thread t1 = new Thread(gen);
        t1.start();
        try {t1.join();} catch (InterruptedException e){}
        gen.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));

        long numOfPrimes = gen.getPrimes().size();
        System.out.println("\n" + numOfPrimes +
                " prime numbers are found in total using 1 thread.");
    }
}
