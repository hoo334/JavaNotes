package desiginpattern.factory;

public class FactoryPatternDemo {
    public static void main(String[] args) {
        ShapeFactory sf = new ShapeFactory();

        Shape s1 = sf.getShape("circle");
        if(s1 != null)s1.draw();

        Shape s2 = sf.getShape("rectangle");
        if(s2 != null)s2.draw();

        Shape s3 = sf.getShape("square");
        if(s3 != null)s3.draw();

        Circle c1 = (Circle)s1;
        if(c1 != null)c1.setRadius(2);
        System.out.println(c1);
    }
}
