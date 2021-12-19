package hw13;

public class WithdrawRunnable implements Runnable{
    private BankAccount account;

    public WithdrawRunnable(BankAccount bankAccount){
        account = bankAccount;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++){
            account.withdraw(100);
        }
    }
}
