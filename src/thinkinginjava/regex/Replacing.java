package thinkinginjava.regex;

public class Replacing {
    static String s = Splitting.knights;

    public static void main(String[] args) {
        System.out.println(s);
        //匹配第一个以 f 开头，后面跟一个或多个字母
        System.out.println(s.replaceFirst("f\\w+","located"));
        //匹配三个单词中任意一个，将其替换
        System.out.println(s.replaceAll("shrubbery|tree|herring","banana"));
    }
}
