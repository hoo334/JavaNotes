package corejava.inheritance;

public class Manager extends Employee {
    private double bonus;

    public Manager(String name,double salary,int year,int month,int day){
        super(name,salary,year,month,day);
        bonus = 0;
    }

    @Override
    public double getSalary() {
        return super.getSalary()+bonus;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double b){
        bonus = b;
    }
}
