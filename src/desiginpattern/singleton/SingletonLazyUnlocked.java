package desiginpattern.singleton;

/*
* 懒加载，线程不安全。
* */
public class SingletonLazyUnlocked {
    private static SingletonLazyUnlocked instance;

    private SingletonLazyUnlocked(){}

    public static SingletonLazyUnlocked getInstance(){
        if(instance != null){
            instance = new SingletonLazyUnlocked();
        }
        return instance;
    }
}
