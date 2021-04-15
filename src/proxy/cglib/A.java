package proxy.cglib;

public class A {
    //被代理的类（目标对象）
    public void execute(){
        System.out.println("---- A.execute() ----");
    }
}
