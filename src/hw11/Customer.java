package hw11;

import java.util.concurrent.locks.ReentrantLock;

public class Customer {

    private Address address;
    private ReentrantLock lock = new ReentrantLock();

    public Customer(Address addr){ address = addr; }

    // Thread-safe getters and setters
    public void setAddress(Address addr){
        lock.lock();
        try { address = addr; } finally { lock.unlock(); }
    }

    public Address getAddress(){
        lock.lock();
        try {return address; } finally { lock.unlock(); }
    }

    public static void main(String[] args){
        Address address1 = new Address("1600 Pennsylvania Ave NW",
                "Washington", "DC", 20500);
        Address address2 = new Address("24 Beacon St", "Boston", "MA", 02133);
        Customer aCustomer = new Customer(address1);

        Thread t1 = new Thread(() -> { System.out.println("t1: " + aCustomer.getAddress()); });
        Thread t2 = new Thread(() -> {
            aCustomer.setAddress(address2);
            System.out.println("t2: " + aCustomer.getAddress());
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) { e.printStackTrace();}
    }
}
