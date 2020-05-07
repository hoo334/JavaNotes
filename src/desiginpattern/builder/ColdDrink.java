package desiginpattern.builder;

/*
* 冷饮抽象类，所有冷饮都是用杯子装，价格不同。
* */
public abstract class ColdDrink implements Item{

    @Override
    public Packing packing() {
        return new Bottle();
    }

    @Override
    public abstract float price();
}
