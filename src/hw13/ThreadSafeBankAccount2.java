package hw13;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

// As provided by Prof. Suzuki
public class ThreadSafeBankAccount2 implements BankAccount{
	private double balance = 0;
	private ReentrantLock lock = new ReentrantLock();
	private Condition sufficientFundsCondition = lock.newCondition();
	private Condition belowUpperLimitFundsCondition = lock.newCondition();
	
	public void deposit(double amount){
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() + 
					" (d): current balance: " + balance);
			while(balance >= 300){
				System.out.println(Thread.currentThread().getId() + 
						" (d): await(): Balance exceeds the upper limit.");
				belowUpperLimitFundsCondition.await();
			}
			balance += amount;
			System.out.println(Thread.currentThread().getId() + 
					" (d): new balance: " + balance);
			sufficientFundsCondition.signalAll();
		}
		catch (InterruptedException exception){
			exception.printStackTrace();
		}
		finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}
	
	public void withdraw(double amount){
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() + 
					" (w): current balance: " + balance);
			while(balance <= 0){
				System.out.println(Thread.currentThread().getId() + 
						" (w): await(): Insufficient funds");
				sufficientFundsCondition.await();
			}
			balance -= amount;
			System.out.println(Thread.currentThread().getId() + 
					" (w): new balance: " + balance);
			belowUpperLimitFundsCondition.signalAll();
		}
		catch (InterruptedException exception){
			exception.printStackTrace();
		}
		finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}
	
	public static void main(String[] args){
		ThreadSafeBankAccount2 bankAccount = new ThreadSafeBankAccount2();

		LinkedList<Thread> depositThreads = new LinkedList<>();
		LinkedList<Thread> withdrawalThreads = new LinkedList<>();
		LinkedList<DepositRunnable> deposits = new LinkedList<>();
		LinkedList<WithdrawRunnable> withdrawals = new LinkedList<>();

		// Create and start threads for withdrawal and deposit.
		for(int i = 0; i < 5; i++){
			DepositRunnable deposit = new DepositRunnable(bankAccount);
			deposits.add(deposit);
			Thread depositThread = new Thread(deposit);
			depositThreads.add(depositThread);
			depositThread.start();

			WithdrawRunnable withdrawal = new WithdrawRunnable(bankAccount);
			withdrawals.add(withdrawal);
			Thread withdrawalThread = new Thread(withdrawal);
			withdrawalThreads.add(withdrawalThread);
			withdrawalThread.start();
		}

		// Two-step thread termination.
		for(int i = 0; i < 5; i++){
			DepositRunnable deposit = deposits.get(i);
			deposit.setDone();
			Thread depositThread = depositThreads.get(i);
			depositThread.interrupt();

			WithdrawRunnable withdrawal = withdrawals.get(i);
			withdrawal.setDone();
			Thread withdrawalThread = withdrawalThreads.get(i);
			withdrawalThread.interrupt();
		}

	}
}
