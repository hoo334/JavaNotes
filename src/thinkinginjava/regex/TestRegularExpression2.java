package thinkinginjava.regex;

public class TestRegularExpression2 {
    public static void main(String[] args) {
        String s = "Java now has regular expressions";
        String[] strs = new String[]{"^Java","\\Breg.*","n.w\\s+h(a|i)s","s?","s*","s+","s{4}","s{1}.","s{0,3}"};
        TestRegularExpression.regexMatch(strs, s);
        System.out.println("==================================");
        System.out.println("Arline ate eight apples and one orange while Anita hadn't any".matches(
                "(?i)((^[aeiou])|(\\s+[aeiou])\\w+?[aeiou]\b)"));
    }
}
