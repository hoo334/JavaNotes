package algorithm.sort;

public class Heap {
    public static void sort(Comparable[] a){
        for(int i=a.length/2-1; i>=0; i--){
            //从第一个非叶子结点开始，从下至上，从右至左调整结构
            adjustDown(a,i,a.length);
        }

        //交换堆顶元素与最后一个元素，最大的元素归位。由于破坏了大根堆的特性需要重新调整堆顶元素。
        for(int j=a.length-1; j>0; j--){
            //交换元素
            Comparable temp = a[0];
            a[0] = a[j];
            a[j] = temp;

            //向下调整大根堆
            adjustDown(a,0,j);
        }
    }

    private static void adjustDown(Comparable[] a,int i,int length){
        Comparable temp = a[i];


        for(int k=2*i+1; k<length; k=k*2+1){

            //找到两个子结点中较大的一个结点
            if(k+1<length && a[k].compareTo(a[k+1])<0){
                k++;
            }
            //如果子结点的值大于父结点的值，将父结点向下调整，继续向下调整
            if(a[k].compareTo(temp) > 0){
                //父结点的值设置为子结点的值
                a[i] = a[k];
                //对当前结点继续向下调整
                i = k;
            }else{
                break;
            }
        }
        //将 temp 值放到最终的位置
        a[i] = temp;
    }
}
