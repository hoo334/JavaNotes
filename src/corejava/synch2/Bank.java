package corejava.synch2;

import java.util.Arrays;

//synchronized 内部锁
public class Bank {
    private double[] accounts;

    public Bank(int n,double initialBalance){
        accounts = new double[n];
        Arrays.fill(accounts,initialBalance);
    }

    public synchronized void transfer(int from,int to,double amount) throws InterruptedException{
        while(accounts[from] < amount)
            wait();//账户余额不足进程转入等待态
        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d",amount,from,to);
        accounts[to] += amount;
        System.out.printf("Total Balance: %10.2f%n",getTotalBalance());
        notifyAll();//此时有账户余额通知等待态进程更新
    }

    public synchronized double getTotalBalance(){
        double sum = 0;
        for (double a : accounts) {
            sum += a;
        }
        return sum;
    }

    public int size(){
        return accounts.length;
    }
}
