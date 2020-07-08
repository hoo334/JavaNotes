package thinkinginjava.regex;
import java.util.*;

public class Splitting {
    public static String knights = "Then, when you have found the shrubbery, you must "+
            "cut down the mightiest tree in the forest... "+
            "with... a herring!";
    public static void split(String regex){
        System.out.println(
                Arrays.toString(knights.split(regex)));
    }

    public static void main(String[] args) {
        split(" "); // 按空格划分字符串
        split("\\W+"); // 在一个或多个非单词处分割，\W 表示非单词字符 \w 表示一个单词字符
        split("n\\W+"); //字母 n 后跟着一个或多个非单词字符处分割
    }
}
