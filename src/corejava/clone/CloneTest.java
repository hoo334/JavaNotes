package corejava.clone;

public class CloneTest {
    public static void main(String[] args) throws CloneNotSupportedException {
    Employee original = new Employee("john",50000);
    original.setHireDay(2000,1,1);
    Employee copy = original.clone();
    copy.raiseSalary(10);
    System.out.println("original="+original);
    System.out.println("copy="+copy);
    }
}
