package proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibProxy implements MethodInterceptor {
    /**
    * 被代理的目标类
    * */
    private A target;

    public CGLibProxy(A target) {
        super();
        this.target = target;
    }

    /**
     * 创建代理对象
     * */
    public A createProxy(){
        //使用 CGLIB 生成代理
        //1.生成增强类实例，用于生产代理类
        Enhancer enhancer = new Enhancer();
        //2.设置被代理类字节码，CGLIB 根据字节码生成被代理类的子类
        enhancer.setSuperclass(target.getClass());
        //3. 设置回调函数，即一个方法拦截
        enhancer.setCallback(this);
        //4.创建代理
        return (A) enhancer.create();
    }



    /**
     * 回调函数
     * @param obj 代理对象
     * @param method 委托类方法
     * @param args 方法参数
     * @param proxy 每个被代理的方法都对应一个MethodProxy对象，
     *                    methodProxy.invokeSuper方法最终调用委托类(目标类)的原始方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        //过滤不需要该业务的方法
        if("execute".equals(method.getName())){
            //调用前验证权限
            System.out.println("---- 鉴权 ----");

            //调用目标对象的方法（执行 A 独享即被代理对象的 execute 方法）
            Object result = proxy.invokeSuper(obj,args);

            //记录日志数据（动态添加其他要执行业务）
            System.out.println("---- 日志 ----");

            return result;
        }else if("delete".equals(method.getName())){
            return proxy.invokeSuper(obj, args);
        }
        //不需要增强直接执行原方法
        return proxy.invokeSuper(obj,args);
    }
}
