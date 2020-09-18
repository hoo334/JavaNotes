package concurrencyinpractice.chap3;
/*
* 3-3 线程安全的可变整数类
* */
public class SynchronizedInteger {
    private int value;

    public synchronized int getValue() {
        return value;
    }

    public synchronized void setValue(int value) {
        this.value = value;
    }
}
