package algorithm.sort;

/*
* insert sort
* */
public class Insertion {
    public static void sort(Comparable[] a){
        int N = a.length;

        //将第 i 个元素与 前面的 0 ~ i-1 个元素比较若比其中的元素小则依次交换
        for(int i=1; i<N; i++){
            for(int j=i; j>0 && a[j].compareTo(a[j-1])<0 ;j--){
               Comparable temp = a[j];
               a[j] = a[j-1];
               a[j-1] = temp;
            }
        }
    }
}
