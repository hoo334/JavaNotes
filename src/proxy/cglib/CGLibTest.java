package proxy.cglib;

public class CGLibTest {
    public static void main(String[] args) {
        A a = new A();

        CGLibProxy cgLibProxy = new CGLibProxy(a);

        A proxy = cgLibProxy.createProxy();

        proxy.execute();
    }
}
