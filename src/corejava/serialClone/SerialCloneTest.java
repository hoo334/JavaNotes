package corejava.serialClone;

import java.io.*;
import java.time.LocalDate;

//利用序列化克隆
public class SerialCloneTest {

    public static void main(String[] args) throws CloneNotSupportedException{
        Employee harry = new Employee("Harry Hacker",35000,1989,10,1);
        //clone harry
        Employee harry2 = (Employee) harry.clone();
        //mutate harry
        harry.raiseSalary(10);

        //now harry and the clone are different
        System.out.println(harry);
        System.out.println(harry2);

    }
}

class SerialCloneable implements Cloneable,Serializable{
    public Object clone() throws CloneNotSupportedException{
        //将对象序列化到输出流中然后将其读回产生的新对象时对现有对象的拷贝。
        try{
            //save the object to a byte array
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try(ObjectOutputStream out = new ObjectOutputStream(bout)){
                out.writeObject(this);
            }

            try(InputStream bin = new ByteArrayInputStream(bout.toByteArray())){
                ObjectInputStream in = new ObjectInputStream(bin);
                return in.readObject();
            }
        }
        catch(IOException | ClassNotFoundException e){
            CloneNotSupportedException e2 = new CloneNotSupportedException();
            e2.initCause(e);
            throw e2;
        }
    }
}

//在对象流中存储或从对象流中恢复的类都要实现Serializable接口
class Employee extends SerialCloneable {
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