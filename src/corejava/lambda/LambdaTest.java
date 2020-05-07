package corejava.lambda;

import javax.swing.*;
import java.util.Arrays;
import java.util.Date;

//This program demonstrates the use of lambda expressions
public class LambdaTest {

    public static void main(String[] args){
        String[] planets = new String[]{"Mercury","Venus","Earth","Mars","Jupiter","Saturn","Uranus","Neptune"};
        System.out.println(Arrays.toString(planets));
        System.out.println("Sorted in dictionary order");
        Arrays.sort(planets);
        System.out.println(Arrays.toString(planets));
        System.out.println("Sorted by length");
        //lambda表达式不需要显式表明返回值它会根据结果推导返回值类型
        Arrays.sort(planets,(first,second)->first.length()-second.length());
        System.out.println(Arrays.toString(planets));
        //当lambda表达式参数只有一个时可以将括号去掉
        Timer t = new Timer(1000,(event)->
                System.out.println("The time is "+ new Date()));
        t.start();

        //keep running until user selects "ok"
        JOptionPane.showMessageDialog(null,"Quit program");
    }
}
