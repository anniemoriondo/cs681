package hw13;

public class DepositRunnable implements Runnable {
    private BankAccount account;

    // Volatile flag.
    volatile boolean done = false;

    public DepositRunnable(BankAccount bankAccount){
        account = bankAccount;
    }

    public void setDone(){ done = true;}

    @Override
    public void run() {
        while (true) {
            if(done){ break;}
            account.deposit(100);
        }
    }
}
