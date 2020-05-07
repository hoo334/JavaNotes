package corejava.pair;

import java.time.LocalDate;

public class PairTest2 {

    public static void main(String[] args) {
        LocalDate[] birthdays =
                {
                        LocalDate.of(1998,12,1),
                        LocalDate.of(1990,1,2),
                        LocalDate.of(1989,2,3)
                };
        Pair<LocalDate> mm = ArrayAlg.minmax(birthdays);
        System.out.println("min = "+mm.getFirst());
        System.out.println("max = "+mm.getSecond());
    }
}

class ArrayAlg{
    //把T限定为实现了Comparable接口的类型
    public static <T extends Comparable> Pair<T> minmax(T[] a){
    if(a == null || a.length == 0)return null;
    T min = a[0];
    T max = a[0];
        for (int i = 0; i < a.length; i++) {
            if(a[i].compareTo(min) <0 )min = a[i];
            if(a[i].compareTo(max) >0 )max = a[i];
        }
        return new Pair<>(min,max);
    }
}