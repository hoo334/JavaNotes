package corejava.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/*
* 1、流并不存储元素 2、流的操作不会修改其数据源，而是会新生成一个流。
* 3、流的操作是尽可能惰性执行的，只有在需要结果时操作才会执行。
* */
public class CountLongWords {
    public static void main(String[] args) throws IOException{
        //read file into string
        String contents = new String(Files.readAllBytes(Paths.get("E:\\IdeaProjects\\alice.txt")), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("\\PL+"));
        //split into words nonletters are delimeeters(分隔符)
        long count = 0;
        for (String word : words) {
            if(word.length() > 10)count++;
        }
        System.out.println(count);

        //stream 和 parallelStream 产生一个用于words列表的stream。
        count = words.stream().filter(x -> x.length()>10).count();
        System.out.println(count);

        count = words.parallelStream().filter(x -> x.length()>10).count();
        System.out.println(count);
    }
}
