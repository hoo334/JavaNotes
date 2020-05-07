package desiginpattern.abstractfactory;

public class AbstractFactoryDemo {
    public static void main(String[] args) {

        //获取形状工厂
        AbstractFactory shapeFactory = FactoryProducer.getFactory("shape");

        if(shapeFactory != null){
            Shape s1 = shapeFactory.getShape("circle");
            if(s1 != null)s1.draw();

            Shape s2 = shapeFactory.getShape("rectangle");
            if(s2 != null)s2.draw();

            Shape s3 = shapeFactory.getShape("square");
            if(s3 != null)s3.draw();
        }

        //获取颜色工厂
        AbstractFactory colorFactory = FactoryProducer.getFactory("color");

        if(colorFactory != null){
            Color c1 = colorFactory.getColor("red");
            if(c1 != null)c1.fill();

            Color c2 = colorFactory.getColor("blue");
            if(c2 != null)c2.fill();

            Color c3 = colorFactory.getColor("green");
            if(c3 != null)c3.fill();
        }

    }
}
