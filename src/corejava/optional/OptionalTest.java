package corejava.optional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class OptionalTest {
    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("E:\\IdeaProjects\\alice.txt")), StandardCharsets.UTF_8);
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));
        //findFirst方法产生流的第一个元素，findAny方法产生流的任意一个元素，如果流为空返回一个空的Optional对象。
        Optional<String> optionalValue = wordList.stream().filter(s -> s.contains("are")).findFirst();

        //orElse方法产生optionalValue的值，或在Optional为空时，产生“No Word”
        System.out.println(optionalValue.orElse("No Word") + " contains are");
        //Optional为空，产生“N/A”
        Optional<String> optionalString = Optional.empty();
        String result = optionalString.orElse("N/A");
        System.out.println("result: "+result);
        //Optional为空调用参数内方法的结果
        result = optionalString.orElseGet(() -> Locale.getDefault().getDisplayName());
        System.out.println("result: "+result);

        try {
            //在Optional为空时抛出IllegalStateException
            result = optionalString.orElseThrow(IllegalStateException::new);
            System.out.println("result: "+result);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        //找出以red开头的单词
        optionalValue = wordList.stream().filter(s -> s.contains("red")).findFirst();
        //如果Optional不为空调用参数内的方法
        optionalValue.ifPresent(s -> System.out.println(s +"contains red"));

        Set<String> results = new HashSet<>();
        optionalValue.ifPresent(results::add);
        //没有元素添加到set中adder为空的Optional
        Optional<Boolean> added = optionalValue.map(results::add);
        System.out.println("added "+added);

        //0.25 开根号 0.5
        System.out.println(inverse(4.0).flatMap(OptionalTest::sequareRoot));
        //空Optional
        System.out.println(inverse(-1.0).flatMap(OptionalTest::sequareRoot));
        //空Optional
        System.out.println(inverse(0.0).flatMap(OptionalTest::sequareRoot));
        //负数开根号 返回空Optional
        Optional<Double> result2 = Optional.of(-4.0).flatMap(OptionalTest::inverse).flatMap(OptionalTest::sequareRoot);
        System.out.println(result2);

    }

    //求倒数
    public static Optional<Double> inverse(Double x){
        return x == 0 ? Optional.empty():Optional.of(1/x);
    }
    //开根号
    public static Optional<Double> sequareRoot(Double x){
        return x < 0 ? Optional.empty():Optional.of(Math.sqrt(x));
    }
}
