package algorithm.test;

import java.util.Queue;

public class Test {
    public static void main(String[] args) {
       count();
    }

    public static void count(){
        int res = 1;
        for(int i=1; i < 100; i++){
            res *= i;
            System.out.println(res);
        }
    }
}
