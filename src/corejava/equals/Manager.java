package corejava.equals;


public class Manager extends Employee {
    private double bonus;

    public Manager(String name, double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
        bonus = 0;
    }

    @Override
    public double getSalary() {
        return super.getSalary() + bonus;
    }

    void setBonus(double b) {
        bonus = b;
    }

    public boolean equals(Object otherObject){
        if(!super.equals(otherObject))return false;
        Manager ohter = (Manager)otherObject;
        return bonus == ohter.bonus;
    }

    public int hashCode(){
        return super.hashCode()+17*new Double(bonus).hashCode();
    }

    public String toString(){
        return super.toString()+"[bonus="+bonus+"]";
    }

}