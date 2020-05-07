package desiginpattern.singleton;

/*
* 非懒加载，线程安全。
* */
public class SingletonHungry {
    private static SingletonHungry instance = new SingletonHungry();

    private SingletonHungry(){}

    public static SingletonHungry getInstance(){
        return instance;
    }
}
