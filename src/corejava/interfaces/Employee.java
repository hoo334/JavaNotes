package corejava.interfaces;

public class Employee implements Comparable<Employee>{
    private String name;
    private double salary;

    public Employee(String name,double salary){
        this.name = name;
        this.salary = salary;
    }

    public String getName(){
        return name;
    }

    public double getSalary(){
        return salary;
    }

    public void raiseSalary(double byPercent){
        salary+=(salary*byPercent)/100;
    }

    //实现compareTo接口实现Employee比较
    public int compareTo(Employee other){
        //根据雇员的薪水来比较雇员的大小
        return Double.compare(salary,other.salary);
    }
}
