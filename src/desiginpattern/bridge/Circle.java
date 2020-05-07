package desiginpattern.bridge;

public class Circle extends Shape {

    private int x, y, radius;

    public Circle(int radius, int x, int y, DrawAPI drawAPI){
        super(drawAPI);
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    public void draw(){
        drawAPI.draw(radius,x,y);
    }
}
