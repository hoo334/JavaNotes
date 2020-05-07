package corejava.clone;

public class CloneDemo {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person person = new Person("zhangsan",20,123);
        Person cloned = person.clone();

        System.out.println("original: "+person);
        System.out.println("cloned: "+cloned);

        System.out.println("Modify Age and Id: ");
        cloned.setAge(55);
        cloned.setId(234);

        System.out.println("original: "+person);
        System.out.println("cloned: "+cloned);
    }
}
