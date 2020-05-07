package algorithm.sort;

public class Shell {
    public static void sort(Comparable[] a){
        int N = a.length;
        int h = 1;
        while(h < N/3) h = 3*h +1;  //h = 1,4,13,40 ...

        while(h >= 1){
            //将数组变为 h 有序，即相隔 h 个元素 的元素构成一组，组内有序，将 h 的值逐渐缩小则数组有序。
            for(int i=h; i<N; i++){
                for(int j=i; j>=h && a[j].compareTo(a[j-h])<0; j-=h){
                    Comparable temp = a[j];
                    a[j] = a[j-h];
                    a[j-h] = temp;
                }
            }
            h = h/3;
        }
    }
}
