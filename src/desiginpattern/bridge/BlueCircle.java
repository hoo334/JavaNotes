package desiginpattern.bridge;

public class BlueCircle implements DrawAPI {

    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: blue, radius: "+radius+", x: "+x+", y: "+y+"]");
    }
}
