package desiginpattern.singleton;

/*
* 懒加载，线程安全。
* */
public class SingletonLazyLocked {
    private static SingletonLazyLocked instance;

    private SingletonLazyLocked(){}

    public static synchronized SingletonLazyLocked getInstance(){
        if(instance != null){
            instance = new SingletonLazyLocked();
        }
        return instance;
    }
}
