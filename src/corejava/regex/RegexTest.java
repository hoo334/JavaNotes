package corejava.regex;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

//提示输入一个模式，然后提示输入用于匹配的字符串，随后打印出输入是否与模式相匹配
//如果输入匹配模式，并且模式包含群组，那么这个程序将用括号打印出群组边界
public class RegexTest {
    public static void main(String[] args) throws PatternSyntaxException {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter pattern: e.g. (([1-9]|1[0-2]):([0-5][0-9]))[ap]m");
        String patternString = in.nextLine();

        Pattern pattern = Pattern.compile(patternString);
        while(true){
            System.out.println("Enter String to match: e.g.11:30am");
            String input  = in.nextLine();
            if(input == null || input.equals(""))return;

            Matcher matcher = pattern.matcher(input);
            if(matcher.matches()){
                System.out.println("Match");
                int g = matcher.groupCount();
                if(g > 0){
                    for (int i = 0; i < input.length(); i++) {
                        //Print any empty groups
                        for (int j = 1; j <= g ; j++)
                            if(i == matcher.start(j) && i == matcher.end(j)) System.out.print("()");
                        //Print ( for non-empty groups starting here
                        for (int j = 1; j <= g ; j++)
                            if(i == matcher.start(j) && i != matcher.end(j)) System.out.print("(");
                        System.out.print(input.charAt(i));
                        //Print ) for non-empty groups starting here
                        for (int j = 1; j <= g ; j++)
                            if(i+1 != matcher.start(j) && i+1 == matcher.end(j)) System.out.print(")");

                    }
                    System.out.println();
                }
            }
            else
                System.out.println("No Match");
        }
    }
}
