package desiginpattern.servicelocator;

public class ServiceLocatorPatternDemo {
    public static void main(String[] args) {
        Service service = ServiceLocator.getService("service1");
        service.execute();

        service = ServiceLocator.getService("service2");
        service.execute();

        service = ServiceLocator.getService("service1");
        service.execute();

        service = ServiceLocator.getService("service2");
        service.execute();

    }
}
