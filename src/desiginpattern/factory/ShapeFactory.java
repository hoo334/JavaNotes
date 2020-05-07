package desiginpattern.factory;

public class ShapeFactory {
    //使用getShape方法获取形状对象。
    public Shape getShape(String shapeType){
        if(shapeType == null){
            return null;
        }

        if(shapeType.equalsIgnoreCase("circle")){
            return new Circle();
        }else if(shapeType.equalsIgnoreCase("square")){
            return new Square();
        }else if(shapeType.equalsIgnoreCase("rectangle")){
            return new Rectangle();
        }else{
            return null;
        }
    }
}
