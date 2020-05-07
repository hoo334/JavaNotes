package corejava.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.DoublePredicate;

//将一个数组分成两部分分别统计再将结果相加
public class forkJoinTest {
    public static void main(String[] args) {
        final int SIZE = 10000000;
        double[] numbers = new double[SIZE];
        for (int i = 0; i < SIZE; i++)numbers[i] = Math.random();
        //x -> x>0.5
        Counter counter = new Counter(numbers,0,numbers.length,x -> x > 0.5);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(counter);
        System.out.println(counter.join());
    }
}

class Counter extends RecursiveTask<Integer>{
    public static final int  THRESHOLD = 1000;
    private double[] values;
    private int from;
    private int to;
    private DoublePredicate filter;

    public Counter(double[] values, int from, int to, DoublePredicate filter) {
        this.values = values;
        this.from = from;
        this.to = to;
        this.filter = filter;
    }

    @Override
    protected Integer compute() {
        //小于1000个直接计算
        if(to - from < THRESHOLD){
            int count = 0;
            for (int i = from; i < to; i++) {
                //统计大于0.5的小数
                if(filter.test(values[i]))count++;
            }
            return count;
        }
        else
        {   //大于1000个分成两个
            int mid = (from + to)/2;
            Counter first = new Counter(values,from,mid,filter);
            Counter second = new Counter(values,mid,to,filter);
            //invokeAll 接收到很多任务并阻塞，知道所有任务都完成。join方法将生成结果。
            invokeAll(first,second);
            return first.join()+second.join();
        }
    }
}