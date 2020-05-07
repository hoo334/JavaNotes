package corejava.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

//This program demonstrate the use of proxies
//假设有一个表示接口的Class对象(有可能只有一个接口)，它的确切类型在编译时无法知道。
// 代理类可以在运行时创建全新的类，这样的代理类可以实现指定的借口。

public class ProxyTest {
    public static void main(String[] args) {
        Object[] elements = new Object[1000];
        for (int i = 0; i < elements.length; i++) {
            Integer value = i+1;
            //新建一个调用处理器
            InvocationHandler handler = new TraceHandler(value);
            //新建一个反射代理
            Object proxy = Proxy.newProxyInstance(null,new Class[]{Comparable.class},handler);

            elements[i] = proxy;
        }
        //construct a random integer
        Integer key = new Random().nextInt(elements.length)+1;
        //折半查找
        int result = Arrays.binarySearch(elements,key);
        if(result >= 0) System.out.println(elements[result]);
    }
}

class TraceHandler implements InvocationHandler{
    private Object target;

    public TraceHandler(Object target) {
        this.target = target;
    }
    //调用处理器必须给出处理调用的invoke()方法
    public Object invoke(Object proxy, Method m,Object[] args)throws Throwable{
        System.out.print(target);
        System.out.print("."+m.getName()+"(");
        if(args != null){
            for (int i = 0; i < args.length; i++) {
                System.out.print(args[i]);
                if(i<args.length-1) System.out.print(",");
            }
        }
        System.out.println(")");
        //invoke actual method
        return m.invoke(target,args);
    }

}