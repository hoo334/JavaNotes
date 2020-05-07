package corejava.staticInnerClass;

import java.util.Arrays;

public class StaticInnerClassTest {
    public static void main(String[] args) {
        double[] d = new double[5];
        for (int i = 0; i <d.length; i++)
            d[i] = 100*Math.random();
        ArrayAlg.Pair p = ArrayAlg.minmax(d);
        System.out.println(Arrays.toString(d));
        System.out.println("min= "+p.getFirst());
        System.out.println("max= "+p.getSecond());
    }
}

class ArrayAlg{
    //静态内部类，内部类没有引用外围类对象因此可声明为static
    public static class Pair{
        private double first;
        private double second;

        public Pair(double first, double second) {
            this.first = first;
            this.second = second;
        }

        public double getFirst() {
            return first;
        }

        public double getSecond() {
            return second;
        }
    }

    //静态方法
    public static Pair minmax(double[] values){
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (double value : values) {
            if(min>value)min=value;
            if(max<value)max=value;
        }
        return new Pair(min,max);
    }

}