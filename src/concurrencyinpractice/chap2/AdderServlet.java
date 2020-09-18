package concurrencyinpractice.chap2;
/*
* 2-1 一个无状态的 Servlet
* 线程安全
* */
public class AdderServlet{

    public void service(Request request, Response response){
        int value = request.getValue();
        System.out.println("Init Value: " + value);
        value += 6;
        System.out.println("Modified Value: " + value);
        request.setValue(value);
    }
}

