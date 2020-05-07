package algorithm.sort;

/*
* select sort
* */
public class Selection {
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {

            int min = i;
            // find the min in the array
            for (int j = i + 1; j < N; j++) {
                if (a[min].compareTo(a[j]) > 0) min = j;
            }

            //exchange the min and i
            Comparable temp = a[min];
            a[min] = a[i];
            a[i] = temp;

        }
    }
}

