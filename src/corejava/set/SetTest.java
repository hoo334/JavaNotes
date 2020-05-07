package corejava.set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

//散列集set散列码可以相同，采用拉链法处理碰撞
public class SetTest {
    public static void main(String[] args) {
        Set<String> words = new HashSet<>();
        long totalTime = 0;
        Scanner in = new Scanner(System.in);
        int wordNum = 0;
        System.out.println("Enter the num of the words:");
        if(in.hasNextInt()){
            wordNum = in.nextInt();
            System.out.println("Enter "+wordNum+" words.");
        }
            for (int i = 0; i < wordNum && in.hasNext(); i++) {
                String word = in.next();
                long callTime = System.currentTimeMillis();
                words.add(word);
                //计算添加一个单词到所用的时间
                callTime = System.currentTimeMillis() - callTime;
                totalTime +=callTime;
            }

        Iterator<String> iter = words.iterator();
        for (int i = 1; i <= 20 && iter.hasNext(); i++)
            System.out.println(iter.next());

        System.out.println("...");
        System.out.println(words.size()+" distinct words."+totalTime+" milliseconds.");

    }
}
