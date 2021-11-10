package hw3;


public class PrimeGenSixteenThreads {

    public static void main(String[] args){
        // Generating primes from 1 to 2 million: Sixteen threads.
        RunnablePrimeGenerator gen1 = new RunnablePrimeGenerator(1L, 125000L);
        RunnablePrimeGenerator gen2 =
                new RunnablePrimeGenerator(125001L, 250000L);
        RunnablePrimeGenerator gen3 =
                new RunnablePrimeGenerator(250001L, 375000L);
        RunnablePrimeGenerator gen4 =
                new RunnablePrimeGenerator(375001L, 500000L);
        RunnablePrimeGenerator gen5 =
                new RunnablePrimeGenerator(500001L, 625000L);
        RunnablePrimeGenerator gen6 =
                new RunnablePrimeGenerator(625001L, 750000L);
        RunnablePrimeGenerator gen7 =
                new RunnablePrimeGenerator(750001L, 875000L);
        RunnablePrimeGenerator gen8 =
                new RunnablePrimeGenerator(875001L, 1000000L);
        RunnablePrimeGenerator gen9 =
                new RunnablePrimeGenerator(1000001L, 1125000L);
        RunnablePrimeGenerator gen10 =
                new RunnablePrimeGenerator(1125001L, 1250000L);
        RunnablePrimeGenerator gen11 =
                new RunnablePrimeGenerator(1250001L, 1375000L);
        RunnablePrimeGenerator gen12 =
                new RunnablePrimeGenerator(1375001L, 1500000L);
        RunnablePrimeGenerator gen13 =
                new RunnablePrimeGenerator(1500001L, 1625000L);
        RunnablePrimeGenerator gen14 =
                new RunnablePrimeGenerator(1625001L, 1750000L);
        RunnablePrimeGenerator gen15 =
                new RunnablePrimeGenerator(1750001L, 1875000L);
        RunnablePrimeGenerator gen16 =
                new RunnablePrimeGenerator(1875001L, 2000000L);


        Thread t1 = new Thread(gen1);
        Thread t2 = new Thread(gen2);
        Thread t3 = new Thread(gen3);
        Thread t4 = new Thread(gen4);
        Thread t5 = new Thread(gen5);
        Thread t6 = new Thread(gen6);
        Thread t7 = new Thread(gen7);
        Thread t8 = new Thread(gen8);
        Thread t9 = new Thread(gen9);
        Thread t10 = new Thread(gen10);
        Thread t11 = new Thread(gen11);
        Thread t12 = new Thread(gen12);
        Thread t13 = new Thread(gen13);
        Thread t14 = new Thread(gen14);
        Thread t15 = new Thread(gen15);
        Thread t16 = new Thread(gen16);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
        t11.start();
        t12.start();
        t13.start();
        t14.start();
        t15.start();
        t16.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
            t7.join();
            t8.join();
            t9.join();
            t10.join();
            t11.join();
            t12.join();
            t13.join();
            t14.join();
            t15.join();
            t16.join();
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
        gen9.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen10.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen11.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen12.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen13.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen14.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen15.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));
        gen16.getPrimes().forEach(
                (Long prime) -> System.out.print(prime + ", "));

        long numOfPrimes = gen1.getPrimes().size() + gen2.getPrimes().size()
                + gen3.getPrimes().size() + gen4.getPrimes().size()
                + gen5.getPrimes().size() + gen6.getPrimes().size()
                + gen7.getPrimes().size() + gen8.getPrimes().size()
                + gen9.getPrimes().size() + gen10.getPrimes().size()
                + gen11.getPrimes().size() + gen12.getPrimes().size()
                + gen13.getPrimes().size() + gen14.getPrimes().size()
                + gen15.getPrimes().size() + gen16.getPrimes().size();
        System.out.println("\n" + numOfPrimes +
                " prime numbers are found in total.");
    }
}
