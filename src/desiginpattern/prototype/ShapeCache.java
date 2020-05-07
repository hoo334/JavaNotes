package desiginpattern.prototype;

import java.util.concurrent.ConcurrentHashMap;

public class ShapeCache {

    private static ConcurrentHashMap<String,Shape> shapeMap = new ConcurrentHashMap<>();

    public static Shape getShape(String shapeId){
        Shape cachedShape = shapeMap.get(shapeId);
        return (Shape)cachedShape.clone();
    }

    //对每种形状进行数据库查询，并创建该形状。
    //假设创建三种形状。
    public static void  loadCache(){
        Circle circle = new Circle();
        circle.setId("1");
        shapeMap.put(circle.getId(),circle);

        Rectangle rectangle = new Rectangle();
        rectangle.setId("2");
        shapeMap.put(rectangle.getId(),rectangle);

        Square square = new Square();
        square.setId("3");
        shapeMap.put(square.getId(),square);
    }
}
