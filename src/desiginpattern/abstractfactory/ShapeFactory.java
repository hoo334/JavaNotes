package desiginpattern.abstractfactory;

public class ShapeFactory extends AbstractFactory{
    @Override
    public Color getColor(String color) {
        return null;
    }

    //使用getShape方法获取形状对象。
    @Override
    public Shape getShape(String shape) {
        if(shape == null){
            return null;
        }
        if(shape.equalsIgnoreCase("circle")){
            return new Circle();
        }else if(shape.equalsIgnoreCase("square")){
            return new Square();
        }else if(shape.equalsIgnoreCase("rectangle")){
            return new Rectangle();
        }
        return null;
    }

}
