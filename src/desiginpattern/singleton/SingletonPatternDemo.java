package desiginpattern.singleton;

public class SingletonPatternDemo {

    public static void main(String[] args) {
        //不合法，构造函数不可见
        //SingleObject so = new SingleObject();

        SingleObject so = SingleObject.getInstance();
        so.showMessage();
    }
}
