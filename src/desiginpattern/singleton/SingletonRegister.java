package desiginpattern.singleton;

/*
* 静态内部类懒加载
* 懒加载，线程安全
* */
public class SingletonRegister {
    private static class SingletonHolder{
        private static final SingletonRegister INSTANCE = new SingletonRegister();
    }

    private SingletonRegister(){}

    public static final SingletonRegister getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
