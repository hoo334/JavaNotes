package corejava.collecting;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectingResults {

    public static Stream<String> noVowels() throws IOException{
        //read file into string
        String contents = new String(Files.readAllBytes(Paths.get("E:\\IdeaProjects\\alice.txt")), StandardCharsets.UTF_8);
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));
        //split into words nonletters are delimeeters(分隔符)
        Stream<String> words = wordList.stream();

        return words;//.map(s -> s.replaceAll("[aeiouAEIOU]","*"));
    }

    public static <T> void show(String label , Set<T> set){
        System.out.println(label + ":"+set.getClass().getName());
        System.out.println("["+set.stream().limit(10).map(Object::toString).collect(Collectors.joining(","))+"]");
    }

    public static void main(String[] args) throws IOException{
        //生成无限流截取前10个元素
        Iterator<Integer> iter = Stream.iterate(0, n -> n+1).limit(10).iterator();
        while(iter.hasNext())
            System.out.print(iter.next());
        System.out.println();

        //将Object数组直接转换为Object数组 -- 错误做法
        Object[] numbers = Stream.iterate(0,n -> n+1).limit(10).toArray();
        System.out.println("Object array:"+numbers);//Note its an Object[] array

        //将Object数组直接转为Integer数组 -- 错误
        Integer[] numbers3 = Stream.iterate(0, n -> n+1).limit(10).toArray(Integer[]::new);
        System.out.println("Integer array: "+numbers3);//Note its an Integer[] array

        try {
            //将Object对象转换为Integer对象
            Integer number = (Integer) numbers[0];//OK
            System.out.println("number: "+number);
            System.out.println("The following statement throws an exception");
            Integer[] number2 = (Integer[]) numbers;//Throws exception
        } catch (ClassCastException e) {
            e.printStackTrace();
        }



        //用收集器来收集到Set中
        Set<String> noVowelSet = noVowels().limit(10).collect(Collectors.toSet());
        show("noVowelSet",noVowelSet);
        //用收集器收集到任意集合中
        TreeSet<String> noVowelTreeSet = noVowels().limit(10).collect(Collectors.toCollection(TreeSet::new));
        show("noVowelTreeSet",noVowelTreeSet);

        //连接Stream中的所有String元素为一个String
        String result = noVowels().limit(10).collect(Collectors.joining());
        System.out.println("Joining: "+result);
        result = noVowels().limit(10).collect(Collectors.joining(","));
        System.out.println("Joining with commas:"+result);

        IntSummaryStatistics summary = noVowels().collect(Collectors.summarizingInt(String::length));

        double averageWordLength = summary.getAverage();
        double maxWordLength = summary.getMax();
        System.out.println("Average word Length: "+averageWordLength);
        System.out.println("Max word Length: "+maxWordLength);
        System.out.println("forEach:");
        //打印前10个单词
        noVowels().limit(10).forEach(System.out::println);
    }
}
