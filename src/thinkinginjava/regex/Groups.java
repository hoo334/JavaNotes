package thinkinginjava.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Groups {
    public static final String POEM = "Twas brillig, and the slithy toves\n" +
            "Did gyre and gimblee in the wabe.\n" +
            "All mimsy were the borogoves, \n" +
            "And the mome raths outgrabe. \n\n" +
            "Beware the Jabberwock, my son, \n" +
            "The jaws that bite, the claws that catch. \n" +
            "Beware the Jubjub bird, and shun\n" +
            "The frumious Bandersnatch.";

    public static void main(String[] args) {
        //捕获每行的最后 3 个词
        Matcher m = Pattern.compile("(?m)(\\S+)\\s+((\\S+)\\s+(\\S+))$").matcher(POEM);
        while(m.find()){
            //依次打印每组的信息
            for(int j = 0; j <= m.groupCount(); j++){
                System.out.println("[" + m.group(j) + "]");
            }
        }
    }
}
