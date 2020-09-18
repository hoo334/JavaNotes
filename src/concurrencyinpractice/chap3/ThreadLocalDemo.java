package concurrencyinpractice.chap3;
/*
* 3-10 使用 ThreadLocal 来维持线程封闭性
* */
class Connection{

}

public class ThreadLocalDemo {
    private static ThreadLocal<Connection> connectionHandler= new ThreadLocal<Connection>(){
        @Override
        protected Connection initialValue() {
            return new Connection();
        }
    };

    public static Connection getConnection(){
        //当第一次调用 get 方法时，initialValue 将会被调用
        return connectionHandler.get();
    }
}
