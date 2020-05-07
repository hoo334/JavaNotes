package algorithm.sort;

public class SortTest {
    public static void main(String[] args) {

        Integer[] list = new Integer[20];
        for (int i = 0; i < 20; i++) {
            list[i] = (int)(Math.random() * 100);
        }

        for (Integer integer : list) {
            System.out.print(integer+" ");
        }
        System.out.println();

        Bubble.sort(list);

        for (Integer integer : list) {
            System.out.print(integer+" ");
        }
    }
}

