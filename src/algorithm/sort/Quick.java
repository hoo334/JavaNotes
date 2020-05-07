package algorithm.sort;

public class Quick {

    public static void  sort(Comparable[] a){
        sort(a,0,a.length-1);
    }

    private static void sort(Comparable[] a, int lo, int hi){
        if(hi <= lo){
            return;
        }
        //切分
        int j = partition(a,lo,hi);
        //将左半部分排序
        sort(a,lo,j-1);
        //将右半部分排序
        sort(a,j+1,hi);
    }

    public static int partition(Comparable[]a, int lo, int hi){
        //将数组切分为a[lo..i-1], a[i], a[i+1..hi]
        int i=lo,j=hi;
        //选取第一个元素为基准点
        Comparable v = a[lo];

        //i，j 相遇停止
        while(i != j){

            //从左边开始，不能保证你最后交换的那个数，是小于等于左边的。例如 2，1，4，9

            //找出右边比基准点小的元素的下标
            while(j > i && a[j].compareTo(v) >= 0){
                j--;
            };

            //找出左边比基准点大的元素的下标
            while(i < j && a[i].compareTo(v) <= 0){
                i++;
            };

            if(i<j){
                //交换a[i] 和 a[j]
                Comparable temp = a[i];
                a[i] = a[j];
                a[j] =temp;
            }
        }

        //i，j 相遇，基准点的位置已经找到，将a[v] 与 a[i] 交换。
        Comparable temp = a[lo];
        a[lo] = a[j];
        a[j] =temp;

        //返回基准点的下标
        return j;
    }


}
