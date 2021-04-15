package proxy.jdkproxy;

public class JDKProxyTest{
    public static void main(String[] args) {
        A a = new A();
        //创建 JDK 代理
        JDKProxy jdkProxy = new JDKProxy(a);
        //创建代理对象
        ExInterface proxy = jdkProxy.createProxy();

        //执行代理对象方法
        proxy.execute();

    }
}