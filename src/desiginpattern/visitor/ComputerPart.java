package desiginpattern.visitor;

public  interface ComputerPart {

    public void accept(ComputerPartVisitor computerPartVisitor);
}