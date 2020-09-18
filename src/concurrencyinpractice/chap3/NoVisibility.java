package concurrencyinpractice.chap3;
/*
* 3-1 在没有同步的情况下共享变量
* 非线程安全
* */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread{
        @Override
        public void run() {
            while(!ready){
                //暂停当前正在执行的线程对象（及放弃当前拥有的cpu资源）,并执行其他线程
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args){
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
