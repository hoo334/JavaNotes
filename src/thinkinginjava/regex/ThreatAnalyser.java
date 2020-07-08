package thinkinginjava.regex;

import java.util.Scanner;
import java.util.regex.MatchResult;

public class ThreatAnalyser {
    static String threatData = "58.27.82.161@02/10/2005\n" +
            "78.17.82.161@03/10/2005\n" +
            "18.27.12.161@03/22/2005\n" +
            "111.27.82.162@04/10/2005\n" +
            "[Next log section with different data format]";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(threatData);
        String pattern = "(\\d+[.]\\d+[.]\\d+[.]\\d+)@" +
                "(\\d{2}/\\d{2}/\\d{4})";
        while(scanner.hasNext(pattern)){
            scanner.next(pattern);
            MatchResult match = scanner.match();
            String ip = match.group(1);
            String date = match.group(2);
            System.out.format("Threat on %s from %s \n", date, ip);

        }
    }

}
