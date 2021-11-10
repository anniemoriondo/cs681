package hw3;


public class PrimeGenTwoThreads {

    public static void main(String[] args){
        // Generating primes from 1 to 2 million: Two threads.
        RunnablePrimeGenerator gen1 = new RunnablePrimeGenerator(1L, 1000000L);
        RunnablePrimeGenerator gen2 =
                new RunnablePrimeGenerator(1000001L, 2000000L);
        Thread t1 = new Thread(gen1);
        Thread t2 = new Thread(gen2);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e){}
        gen1.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen2.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));

        long numOfPrimes = gen1.getPrimes().size() + gen2.getPrimes().size();
        System.out.println("\n" + numOfPrimes +
                " prime numbers are found in total.");
    }
}
