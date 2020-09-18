package concurrencyinpractice.chap1;

/*
* 1-1 非线程安全的数值序列生成器
* */
public class UnsafeSequence {
    private int value;

    /*
    * 返回一个独一无二的值
    * */
    public int getNext(){
        return value++;
    }

    public static void main(String[] args) {
        UnsafeSequence sequence = new UnsafeSequence();

        for(int i = 0; i < 10; i++){
            Thread t = new Thread(() -> {
                System.out.print(sequence.getNext() + "\t");
            });
            t.start();
        }
    }
}
