package thinkinginjava.regex;

public class Rudolph {
    public static void main(String[] args) {
        String[] s = new String[]{"Rudolph","[rR]udolph","[rR][aeiou][a-z]ol.*","R.*"};
        for (String pattern : s) {
            System.out.println("Rudolph".matches(pattern));
        }
    }
}
