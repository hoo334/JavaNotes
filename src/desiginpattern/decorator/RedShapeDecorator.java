package desiginpattern.decorator;

public class RedShapeDecorator extends ShapeDecorator {

    public RedShapeDecorator(Shape shapeDecorator) {
        super(shapeDecorator);
    }

    @Override
    public void draw() {
        super.draw();
        setRedBorder(shapeDecorator);
    }

    private void setRedBorder(Shape decoratedShape){
        System.out.println("Board Color: Red");
    }
}
