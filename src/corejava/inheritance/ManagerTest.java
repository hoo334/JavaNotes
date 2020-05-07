package corejava.inheritance;

public class ManagerTest {
    /*
    * @time 2019-12-25
    * @author liminghu
    * */
    public static void main(String[] args){
       Employee[] staff = new Employee[3];
       Manager boss = new Manager("Carl Carker",75000,1987,12,15);
       boss.setBonus(5000);
        staff[0] = boss;
        staff[1] = new Employee("Harry Hacker",50000,1989,10,1);
        staff[2] = new Employee("Tony Tester",40000,1990,3,15);

        //print information about all Employee Objects
        for(Employee e :staff)
            System.out.println("name="+e.getName()+",salary="+e.getSalary()+",hireDay="+e.getHireDay());
    }
}
