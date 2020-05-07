package desiginpattern.proxy;

public class ProxyPatternDemo {
    public static void main(String[] args) {
        Image image = new ProxyImage("test.jpg");

        //从磁盘加载
        image.display();
        //不需要从磁盘加载
        image.display();
    }
}
