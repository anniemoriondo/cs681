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
}
