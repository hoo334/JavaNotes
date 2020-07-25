package concurrencyinpractice;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/*
* 生成素数，不可靠的取消操作将生产者置于阻塞的操作中
* */
public class BrokenPrimeProducer extends Thread{
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled;

    public BrokenPrimeProducer(BlockingQueue<BigInteger> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while(!cancelled){
                queue.put(p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cancel(){
        this.cancelled = true;
    }
}
