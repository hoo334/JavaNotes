package desiginpattern.frontcontroller;

public class FrontControllerPatternDemo {
    public static void main(String[] args) {
        FrontController frontController = new FrontController();

        frontController.dispatchRequest("Home");

        frontController.dispatchRequest("Student");
    }
}
