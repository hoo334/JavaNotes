package desiginpattern.singleton;
/*
* 懒加载，线程安全
* */
public class SingletonDCL {
    private volatile static SingletonDCL instance;

    private SingletonDCL(){}

    public static SingletonDCL getInstance(){
        if(instance == null){
            synchronized (SingletonDCL.class){
                if(instance == null){
                    instance = new SingletonDCL();
                }
            }
        }
        return instance;
    }

}
