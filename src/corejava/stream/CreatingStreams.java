package corejava.stream;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreatingStreams {
    public static  <T> void show(String title , Stream<T> stream){
        final int SIZE = 10;
        List<T> firstElements = stream.limit(SIZE+1).collect(Collectors.toList());
        //取流的前10个元素输出
        System.out.println(title+": ");
        for (int i = 0; i < firstElements.size(); i++) {
            if(i > 0) System.out.print(", ");
            if(i < SIZE) System.out.print(firstElements.get(i));
            else System.out.print("...");
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        //read file into string
        Path path = Paths.get("E:\\IdeaProjects\\alice.txt");
        String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        Stream<String> words = Stream.of(contents.split("\\PL+"));
        show("words",words);
        //构建任意数量引元的流
        Stream<String> songs = Stream.of("gently","down","the","stream");
        show("songs",songs);
        //创建不含任何元素的流
        Stream<String> silence = Stream.empty();
        show("silence",silence);
        //创建无限流 无限个Echos
        Stream<String> echos = Stream.generate(() -> "Echos");
        show("ehcos",echos);
        //无限个随机数的流
        Stream<Double> randoms = Stream.generate(Math::random);
        show("randoms",randoms);
        //产生无限序列 1，2，3 。。。
        Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE,n -> n.add(BigInteger.ONE));
        show("Integers",integers);
        //利用Pattern类产生流
        Stream<String> wordsAnotherWay = Pattern.compile("\\PL+").splitAsStream(contents);
        show("wordsAnotherWay",wordsAnotherWay);
        //静态的Files.lines方法会返回一个包含文件中所有行的Stream
        try(Stream<String> lines = Files.lines(path,StandardCharsets.UTF_8)){
            show("lines",lines);
        }
    }
}
