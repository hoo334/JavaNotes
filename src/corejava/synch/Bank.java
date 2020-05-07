package corejava.synch;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//锁是可重入的，锁保持一个持有计数来跟踪lock方法的嵌套调用
//例如transfer方法调用getTotalBalance方法此时bankLock计数为2，退出getTotalBalance方法时计数为1，退出transfer时计数为0.
public class Bank {
    private double[] accounts;
    private Lock bankLock;//锁对象
    private Condition sufficientFunds;//条件对象

    public Bank(int n,double initialBalance){
        accounts = new double[n];
        Arrays.fill(accounts,initialBalance);
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }

    public void transfer(int from,int to,double amount){
        bankLock.lock();
        try {

            while(accounts[from] < amount)
                sufficientFunds.await();//账户余额不足进程转入等待态
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d",amount,from,to);
            accounts[to] += amount;
            System.out.printf("Total Balance: %10.2f%n",getTotalBalance());
            sufficientFunds.notifyAll();//此时有账户余额通知等待态进程更新

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bankLock.unlock();
        }
    }

    public double getTotalBalance(){
        bankLock.lock();
        try {
                double sum = 0;
                for (double a : accounts) {
                    sum += a;
                }
                return sum;

        } finally {
            bankLock.unlock();
        }
    }

    public int size(){
        return accounts.length;
    }
}
