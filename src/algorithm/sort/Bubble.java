package algorithm.sort;

public class Bubble {
    public static void sort(Comparable[] a){
        int N = a.length;

        for(int i=0; i<N-1; ++i){
            for(int j=1; j<N-i; j++){
                if(a[j].compareTo(a[j-1]) < 0){
                    Comparable temp = a[j];
                    a[j] = a[j-1];
                    a[j-1] =temp;
                }
            }
        }
    }
}
