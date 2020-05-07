package corejava.linkedList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/*
* Iterator 像一个光标 例如在list a 中 a b c d 四个元素迭代器初始位置 |(iter) a b c d
* 调用iter.next() 变成  a |(iter) b c d 执行iter.remove()删除光标左边的元素 需要注意：remove()不能连续使用
* 需要连续删除元素 将 iter.next()、iter.remove()连续执行
* */
public class LinkedListTest {
    public static void main(String[] args) {
        List<String> a = new LinkedList<>();
        a.add("Amy");a.add("Carl");a.add("Erica");

        List<String> b = new LinkedList<>();
        b.add("Bob");b.add("Doug");b.add("Frances");b.add("Gloria");
        System.out.println(a);
        System.out.println(b);
        //merge the words from b into a
        ListIterator<String> aIter = a.listIterator();
        Iterator<String> bIter = b.iterator();
        //merge
        while(bIter.hasNext()){
            if(aIter.hasNext())aIter.next();
            aIter.add(bIter.next());
        }
        System.out.println(a);

        bIter = b.iterator();
        //删除b中偶数位置的元素
        while (bIter.hasNext()){
            bIter.next();
            if(bIter.hasNext()){
                bIter.next();
                bIter.remove();
            }
        }
        System.out.println(b);

        //remove all words in b from a
        a.removeAll(b);
        System.out.println(a);

    }
}
