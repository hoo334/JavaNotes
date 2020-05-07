package corejava.inheritance;

import java.time.LocalDate;

public class Employee {
    private String name;
    private double salary;
    private LocalDate hireDay;

    public Employee(){

    }

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String n, double s, int year, int month, int day){
        this.name = n;
        this.salary = s;
        hireDay = LocalDate.of(year,month,day);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", hireDay=" + hireDay +
                '}';
    }

    public String getName(){
        return name;
    }

    public double getSalary(){
        return salary;
    }

    public LocalDate getHireDay(){
        return hireDay;
    }

    public void raiseSalary(double byPercent){
        double raise = salary*byPercent/100;
        salary+=raise;
    }
}
