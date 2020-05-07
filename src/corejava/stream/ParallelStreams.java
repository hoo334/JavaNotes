package corejava.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class ParallelStreams {
    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("E:\\IdeaProjects\\alice.txt")), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("\\PL+"));

        //Very bad code ahead
        //int数组被并发访问
        int[] shortWords = new int[10];
        words.parallelStream().forEach(s -> {
            if(s.length() < 10) shortWords[s.length()]++;
        });
        System.out.println(Arrays.toString(shortWords));

        //Try again -- the result will likely be different (and also wrong)
        Arrays.fill(shortWords,0);
        words.parallelStream().forEach(s -> {
            if(s.length() < 10) shortWords[s.length()]++;
        });
        System.out.println(Arrays.toString(shortWords));

        //Remedy:Group and count
        Map<Integer,Long> shortWordsCount = words.parallelStream()
                .filter(s -> s.length()<10)
                .collect(groupingBy(String::length,counting()));
        System.out.println(shortWordsCount);

        //Downstream order not deterministic，下游收集器顺序不重要
        Map<Integer,List<String>> result = words.parallelStream().collect(
                Collectors.groupingByConcurrent(String::length));
        System.out.println(result.get(8));

        result = words.parallelStream().collect(
                Collectors.groupingByConcurrent(String::length));
        System.out.println(result.get(8));

        //统计所有单词长度的个数
        Map<Integer,Long> wordCounts = words.parallelStream().collect(
                groupingByConcurrent(String::length,counting()));
        System.out.println(wordCounts);

        //打印列表中所有单词
        Map<Integer,List<String>> wordsArray = words.parallelStream().collect(
                groupingByConcurrent(String::length));
        System.out.println(wordsArray);

    }
}
