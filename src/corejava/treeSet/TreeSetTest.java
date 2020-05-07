package corejava.treeSet;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

//树集是一个有序集合，可以任意顺序插入到集合中，在对集合进行遍历时每个值自动地按照排序后的顺序呈现
public class TreeSetTest {
    public static void main(String[] args) {
        SortedSet<Item> parts = new TreeSet<>();
        parts.add(new Item("Toster",9234));
        parts.add(new Item("Widget",4333));
        parts.add(new Item("Modem",7777));
        System.out.println(parts);

        //新建一个比较器来按描述信息排序
        NavigableSet<Item> sortByDescription = new TreeSet<>(
                Comparator.comparing(Item::getDescription));

        sortByDescription.addAll(parts);
        System.out.println(sortByDescription);
    }
}
