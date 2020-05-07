package corejava.interfaces;

import java.util.Arrays;

public class EmployeeSortTest {

public static void main(String[] args){
    Employee[] staff = new Employee[3];
    staff[0] = new Employee("Carl Carker",75000);
    staff[1] = new Employee("Harry Hacker",50000);
    staff[2] = new Employee("Tony Tester",40000);
    Arrays.sort(staff);
    for(Employee e:staff)
        System.out.println("Name:"+e.getName()+"salay:"+e.getSalary());
}

}
