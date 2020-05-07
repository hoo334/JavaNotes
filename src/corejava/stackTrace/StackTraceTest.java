package corejava.stackTrace;

import java.util.Scanner;

//打印求阶层堆栈情况
public class StackTraceTest {
    public static int factorial(int n){
        System.out.println("factorial("+n+"):");

        Throwable t = new Throwable();
        //获取堆栈信息数组
        StackTraceElement[] frames = t.getStackTrace();

        for (StackTraceElement frame : frames)
            System.out.println(frame);
        int r;
        if(n<=1)r = 1;
        else r = n*factorial(n-1);
        System.out.println("return"+r);
        return r;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter n :");
        int n = in.nextInt();
        factorial(n);
    }

}
