package desiginpattern.builder;

/*
* 汉堡抽象类
* 汉堡都是用包装纸包装，价格不同。
* */
public abstract class Burger implements Item{

    @Override
    public Packing packing() {
        return new Wrapper();
    }

    @Override
    public abstract float price();
}
