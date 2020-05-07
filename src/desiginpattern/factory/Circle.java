package desiginpattern.factory;

public class Circle implements Shape {


    private int radius;

    @Override
    public void draw() {
        System.out.println("Circle::draw()");
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                '}';
    }
}
