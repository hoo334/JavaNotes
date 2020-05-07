package corejava.regex;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class HrefMatch {
    public static void main(String[] args) {

        try {
            //get URL string from command line or use default
            String urlString;
            if(args.length > 0) urlString = args[0];
            else urlString = "http://baidu.com";

            //open reader for url
            //url 内包含http://www.baidu.com/字段
            InputStreamReader in = new InputStreamReader(new URL(urlString).openStream(), StandardCharsets.UTF_8);

            //read contents into string builder
            StringBuilder input = new StringBuilder();
            int ch;
            while((ch = in.read()) != -1)
                input.append((char)ch);

            //search for all occurrences of pattern
            //+ 一个或多个 * 0个或多个
            String patterString = "http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\\(\\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+";
            Pattern pattern = Pattern.compile(patterString,Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            //使用find方法来部分匹配
            while(matcher.find()){
                String match = matcher.group();
                System.out.println(match);
            }
        } catch (IOException | PatternSyntaxException e) {
            e.printStackTrace();
        }
    }
}
