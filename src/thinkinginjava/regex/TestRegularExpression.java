package thinkinginjava.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegularExpression {
    public static void main(String[] args) {
        String[] strs = new String[]{"abcabcabcdefabc","abc+","(abc)+","(abc){2,}"};
        regexMatch(strs,strs[0]);
    }

    public static void regexMatch(String[] patterns, String s){
        System.out.println("Input: \" " + s + " \" ");
        for (String str : patterns) {
            System.out.println("Regular Expression: \" " + str + "\"");
            //Pattern 对象表示编译后的正则表达式
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(s);
            while(m.find()){
                System.out.println("Match \" " + m.group() + "\" at positions " + m.start() + "-" + (m.end() - 1));
            }
        }
    }
}
