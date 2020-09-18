package concurrencyinpractice.chap1;
/*
* 1-2 线程安全的数值序列生成器
* */
public class Sequence {
    private int nextValue;

    public synchronized int getNext() {
        return nextValue++;
    }

    public static void main(String[] args) {
        Sequence sequence = new Sequence();

        for(int i = 0; i < 10; i++){
            Thread t = new Thread(() -> {
                System.out.print(sequence.getNext() + "\t");
            });
            t.start();
        }
    }
}