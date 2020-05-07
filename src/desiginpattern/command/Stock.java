package desiginpattern.command;

public class Stock {

    private String name = "ABC";
    private int quantity = 10;

    public void buy(){
        System.out.println("Stick [ Name: "+name+",Quantity: "+quantity+" ] bought");
    }

    public void sell(){
        System.out.println("Stick [ Name: "+name+",Quantity: "+quantity+" ] sold");
    }
}
