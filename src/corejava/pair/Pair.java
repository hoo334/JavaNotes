package corejava.pair;
/*
* 泛型约束和局限性
* 1、不能用基本类型实例化类型参数
* 2、运行时类型查询只适用于原始类型
* 3、不能创建参数化类型的数组，补救方法：使用ArrayList:ArrayList<Pair<String>>
* 4、Varargs警告 ：向参数可变的方法中传递一个泛型实例
* 5、不能实例化类型变量 new T() //error
* 6、不能构造泛型数组
* 7、泛型类的静态上下文中类型变量无效
* 8、不能抛出或捕获泛型类的实例
* 9、可以消除对受查异常的检查 @SuppressWarnings("unchecked")
* 10、注意类型擦除后的冲突
* */
public class Pair<T> {
private T first;
private T second;

    public Pair() {
        first = null;second = null;
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }
}
