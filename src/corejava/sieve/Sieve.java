package corejava.sieve;

import java.util.BitSet;

//计算2~2000000之间的素数
public class Sieve {
    public static void main(String[] args) {
        int n = 2000000;
        long start = System.currentTimeMillis();
        //新建一个2000001位的位集
        BitSet b = new BitSet(n+1);
        int count = 0;
        int i;
        //将所有的位设置为1状态
        for(i = 2;i<= n;++i)
            b.set(i);
        i = 2;
        while(i*i <= n){
            if(b.get(i)) {
                count++;

                //i的n倍数(n>=2)肯定不是素数所以置位为0
                int k = 2 * i;
                while(k <= n)
                {
                    b.clear(k);
                    k += i;
                }
            }
            //继续判断
            i++;
        }
        //i此时已经走到数组中间 现在统计后半数组中的素数
        while(i <= n){
            if(b.get(i))count++;
            i++;
        }
        long end = System.currentTimeMillis();
        System.out.println(count+" primes");
        System.out.println((end-start)+" milliseconds");
    }
}
