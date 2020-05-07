package desiginpattern.decorator;

public class DecoratorPatternDemo {

    public static void main(String[] args) {
        Shape circle = new Circle();
        ShapeDecorator redCircle = new RedShapeDecorator(new Circle());
        ShapeDecorator redRectangle = new RedShapeDecorator(new Rectangle());

        System.out.println("Circle with normal boarder");
        circle.draw();

        System.out.println("\nCircle of red boarder");
        redCircle.draw();

        System.out.println("\nRectangle of red boarder");
        redRectangle.draw();
    }
}
