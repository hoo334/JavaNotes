package corejava.shuffle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShuffleTest {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 50 ; i++)
            numbers.add(i);
        //随机打乱元素
        Collections.shuffle(numbers);
        List<Integer> winningCombination = numbers.subList(0,6);//下标0 开始的6个元素
        System.out.println(winningCombination);
        Collections.sort(winningCombination);
        System.out.println(winningCombination);
    }
}
