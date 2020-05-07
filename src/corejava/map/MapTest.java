package corejava.map;

import corejava.inheritance.Employee;

import java.util.HashMap;
import java.util.Map;

//map的键必须是唯一的，不能对同一个键存放两个值，散列函数或比较函数只能作用于键。
public class MapTest {
    public static void main(String[] args) {
        Map<String, Employee> staff = new HashMap<>();
        staff.put("144-25-5464",new Employee("Amy Lee"));
        staff.put("567-25-5464",new Employee("Harry Hacker"));
        staff.put("123-25-5464",new Employee("Gary Cooper"));
        staff.put("456-62-5523",new Employee("Francesca Cruz"));
        System.out.println(staff);

        staff.remove("567-25-5464");
        //put已存在的键值会覆盖之前的数据
        System.out.println(staff.put("456-62-5523",new Employee("Francesca Miller"))+" has been overwrite.");
        System.out.println(staff.get("123-25-5464"));
        staff.forEach((k,v)->
                System.out.println("Key = "+k+",value = "+v));
    }
}
