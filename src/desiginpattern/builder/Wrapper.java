package desiginpattern.builder;

/*
* 包装纸类
* */
public class Wrapper implements Packing {
    @Override
    public String pack() {
        return "Wrapper";
    }
}
