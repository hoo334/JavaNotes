package concurrencyinpractice;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
/*
* 生成素数，测试取消线程
* */
public class PrimeGenerator implements Runnable{
    private final List<BigInteger> primes = new ArrayList<>();
    private volatile boolean cancelled;

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while(!cancelled){
            p = p.nextProbablePrime();//生成下一个素数
            synchronized (this){
                primes.add(p);
            }
        }
    }

    public void cancel(){
        this.cancelled = true;//改变线程状态
    }

    public synchronized List<BigInteger> get(){
        return new ArrayList<BigInteger>(primes);
    }
}

class Main{
    public static List<BigInteger> aSecondOfPrimes() throws InterruptedException{
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try {
            SECONDS.sleep(1);
        } finally {
            generator.cancel();
        }
    return generator.get();
    }

    public static void main(String[] args) throws InterruptedException{
        List<BigInteger> list = aSecondOfPrimes();
        for (BigInteger bigInteger : list) {
            System.out.println(bigInteger);
        }
    }
}




