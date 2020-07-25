package concurrencyinpractice;

import java.util.concurrent.CountDownLatch;
/*
* 闭锁
* */
public class TestHarness {
    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException{
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for(int i = 0; i < nThreads; i++){
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }

        long start = System.nanoTime();
        //起始门减一，开始计时
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end - start;

    }



    public static void main(String[] args) throws InterruptedException{
        TestHarness t = new TestHarness();
        System.out.println(t.timeTasks(4,new MyRunnable()));
    }
}

class MyRunnable implements Runnable{
    private static int count = 0;

    @Override
    public void run() {
        System.out.println("Thread: " + count++);
    }
}