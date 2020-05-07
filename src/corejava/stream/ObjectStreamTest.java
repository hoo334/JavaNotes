package corejava.stream;
import java.io.*;
import java.time.LocalDate;

public class ObjectStreamTest {
    public static void main(String[] args) throws IOException,ClassNotFoundException {

        Employee harry = new Employee("Harry Hacker",50000,1989,10,1);
        Manager carl = new Manager("Carl Craker",80000,1987,12,15);
        carl.setSecretary(harry);
        Manager tony = new Manager("Tony Tester",40000,1990,3,15);
        tony.setSecretary(harry);

        Employee[] staff = new Employee[3];
        staff[0] = carl;
        staff[1] = harry;
        staff[2] = tony;

        //save all employee records to the file employee.dat
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employee.dat"))){
            out.writeObject(staff);
        }

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("employee.dat"))){
            //retrieve all records into a new array
            Employee[] newStaff = (Employee[]) in.readObject();
            //raise secretary's salary
            newStaff[1].raiseSalary(10);

            //print the newly employee records
            for (Employee employee : newStaff) {
                System.out.println(employee);
            }
        }
    }
}

//在对象流中存储或从对象流中恢复的类都要实现Serializable接口
class Employee implements Serializable{
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

class Manager extends Employee {
    private double bonus;
    private Employee secretary;

    public void setSecretary(Employee secretary) {
        this.secretary = secretary;
    }

    public Manager(String name, double salary, int year, int month, int day){
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
