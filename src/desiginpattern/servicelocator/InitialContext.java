package desiginpattern.servicelocator;

public class InitialContext {

    public Object lookup(String jndiName){
        if(jndiName.equalsIgnoreCase("service1")){
            System.out.println("Looking up and creating a new Service1 Object");
            return new Service1();
        }else if(jndiName.equalsIgnoreCase("service2")){
            System.out.println("Looking up and creating a new Service2 Object");
            return new Service2();
        }else{
            return null;
        }
    }
}
