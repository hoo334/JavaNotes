package datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Trie {

    private Node root;
    private int size;

    public Trie(){
        root = new Node();
    }
    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    /*
     * 插入单词
     * @param word 需要插入的单词
     * */
    public void add(String word){
        Node current = root;
        char[] str = word.toCharArray();
        for(char c : str){
            //获取下一个结点
            Node next = current.next.get(c);
            if(next == null){
                //结点为空
                current.next.put(c, new Node());
            }
            //继续向下
            current = current.next.get(c);
        }
        //如果当前单词已经存在，则不需要增加size
        if(!current.isEnd){
            size++;
            current.isEnd = true;
        }
    }

    /*
     * 查询字典树中是否存在单词
     * @param word 需要查询的单词
     * @return 存在单词返回 true，否则返回 false
     * */
    public boolean contains(String word){
        Node current = root;
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            Node node = current.next.get(c);
            if(node == null){
                return false;
            }
            current = node;
        }
        // 字典树中有 panda，查询 pan 返回 false
        return current.isEnd;
    }

    /*
     * 查询字典树中是否存在某个前缀
     * @param prefix 一个字符串前缀
     * @return 存在前缀返回 true，否则返回 false
     * */
    public boolean containsPrefix(String prefix){
        Node current = root;
        for(int i = 0; i < prefix.length(); i++){
            char c = prefix.charAt(i);
            Node node = current.next.get(c);
            if(node == null){
                return false;
            }
            current = node;
        }

        // 字典树中有 panda，查询 pan这个前缀，返回 true
        return true;
    }

    public boolean remove(String word){
        Node multiChildNode = null;
        int multiChildNodeIndex = -1;
        Node current = root;
        for(int i = 0; i < word.length(); i++){
            Node child = current.next.get(word.charAt(i));
            if(child == null){
                //字典树中不存在这个单词
                return false;
            }

            //维护最后一个分叉结点
            if(child.next.size() > 1){
                multiChildNodeIndex = i;
                multiChildNode = child;
            }
            current = child;
        }

        //如果单词后面还有子节点,证明该单词是某个单词的前缀
        if(current.next.size() > 0){
            if(current.isEnd){
                //将其单词标识改为false
                current.isEnd = false;
                //单词树减一
                size-- ;
                return true;
            }
            //不存在该单词，该单词只是前缀
            return false;
        }

        //如果单词的所有字母都没有多个分支，删除整个单词
        if(multiChildNodeIndex == -1){
            //移除单词的第一个字母（移除整个单词）
            root.next.remove(word.charAt(0));
            size-- ;
            return true;
        }

        //如果单词除了最后一个字母，其他的字母有分支
        if(multiChildNodeIndex != word.length() - 1){
            //移除掉分叉结点后的所有结点
            multiChildNode.next.remove(word.charAt(multiChildNodeIndex + 1));
            size-- ;
            return true;
        }
        return false;
    }

    private static class Node{
        public boolean isEnd;//是否存在以该结点为末尾的单词
        public Map<Character, Node> next;//子节点

        public Node(){
            next = new TreeMap<>();
        }

        public Node(boolean isEnd){
            this.isEnd = isEnd;
        }

    }

    public static void main(String[] args) {
        Trie tree = new Trie();
        tree.add("panda");
        tree.add("pan");
        tree.add("pad");

        System.out.println(tree.contains("pad"));
        System.out.println(tree.contains("pand"));
        System.out.println("Size: " + tree.size());
        System.out.println(tree.remove("pad"));
        System.out.println("Size: " + tree.size());
        System.out.println(tree.remove("pan"));
        System.out.println("Size: " + tree.size());
        System.out.println(tree.remove("panz"));
        System.out.println("Size: " + tree.size());
        System.out.println(tree.remove("panda"));
        System.out.println("Size: " + tree.size());
        tree.add("panz");
        System.out.println(tree.contains("panz"));
        System.out.println("Size: " + tree.size());

        List<Integer> list = new ArrayList<>();

    }


}
