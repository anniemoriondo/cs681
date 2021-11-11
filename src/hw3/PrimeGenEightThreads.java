package hw3;


import hw5.RunnablePrimeGenerator;

public class PrimeGenEightThreads {

    public static void main(String[] args){
        // Generating primes from 1 to 2 million: Eight threads.
        RunnablePrimeGenerator gen1 = new RunnablePrimeGenerator(1L, 250000L);
        RunnablePrimeGenerator gen2 =
                new RunnablePrimeGenerator(250001L, 500000L);
        RunnablePrimeGenerator gen3 =
                new RunnablePrimeGenerator(500001L, 750000L);
        RunnablePrimeGenerator gen4 =
                new RunnablePrimeGenerator(750001L, 1000000L);
        RunnablePrimeGenerator gen5 =
                new RunnablePrimeGenerator(1000001L, 1250000L);
        RunnablePrimeGenerator gen6 =
                new RunnablePrimeGenerator(1250001L, 1500000L);
        RunnablePrimeGenerator gen7 =
                new RunnablePrimeGenerator(1500001L, 1750000L);
        RunnablePrimeGenerator gen8 =
                new RunnablePrimeGenerator(1750001L, 2000000L);


        Thread t1 = new Thread(gen1);
        Thread t2 = new Thread(gen2);
        Thread t3 = new Thread(gen3);
        Thread t4 = new Thread(gen4);
        Thread t5 = new Thread(gen5);
        Thread t6 = new Thread(gen6);
        Thread t7 = new Thread(gen7);
        Thread t8 = new Thread(gen8);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
            t7.join();
            t8.join();
        } catch (InterruptedException e){}
        gen1.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen2.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen3.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen4.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen5.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen6.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen7.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen8.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));

        long numOfPrimes = gen1.getPrimes().size() + gen2.getPrimes().size()
                + gen3.getPrimes().size() + gen4.getPrimes().size()
                + gen5.getPrimes().size() + gen6.getPrimes().size()
                + gen7.getPrimes().size() + gen8.getPrimes().size();
        System.out.println("\n" + numOfPrimes +
                " prime numbers are found in total.");
    }
}
