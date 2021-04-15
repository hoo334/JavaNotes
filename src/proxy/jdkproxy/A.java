package proxy.jdkproxy;

public class A implements ExInterface {
    @Override
    public void execute() {
        System.out.println("---- A.execute() ----");
    }
}
