package hw13;

public class DepositRunnable implements Runnable {
    private BankAccount account;

    public DepositRunnable(BankAccount bankAccount){
        account = bankAccount;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++){
            account.deposit(100);
        }
    }
}
