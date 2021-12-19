package hw13;

public class WithdrawRunnable implements Runnable{
    private BankAccount account;

    // Volatile flag.
    volatile boolean done = false;

    public WithdrawRunnable(BankAccount bankAccount){ account = bankAccount; }

    public void setDone() { done = true; }

    @Override
    public void run() {
        while (true) {
            if(done){ break;}
            account.withdraw(100);
        }
    }
}
