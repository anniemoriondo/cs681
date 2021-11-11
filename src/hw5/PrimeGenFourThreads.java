package hw5;


public class PrimeGenFourThreads {

    public static void main(String[] args){
        // Generating primes from 1 to 2 million: Four threads.
        RunnableCancellablePrimeGenerator gen1 =
                new RunnableCancellablePrimeGenerator(1L, 500000L);
        RunnableCancellablePrimeGenerator gen2 =
                new RunnableCancellablePrimeGenerator(500001L, 1000000L);
        RunnableCancellablePrimeGenerator gen3 =
                new RunnableCancellablePrimeGenerator(1000001L, 1500000L);
        RunnableCancellablePrimeGenerator gen4 =
                new RunnableCancellablePrimeGenerator(1500001L, 2000000L);

        Thread t1 = new Thread(gen1);
        Thread t2 = new Thread(gen2);
        Thread t3 = new Thread(gen3);
        Thread t4 = new Thread(gen4);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e){}
        gen1.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen2.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen3.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen4.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));

        long numOfPrimes = gen1.getPrimes().size() + gen2.getPrimes().size()
                + gen3.getPrimes().size() + gen4.getPrimes().size();
        System.out.println("\n" + numOfPrimes +
                " prime numbers are found in total using 4 threads.");
    }
}
