package concurrencyinpractice.chap2;

import java.util.concurrent.atomic.AtomicLong;
/*
* 2-4 使用 AtomicLong 类型的变量来统计已处理请求的数量
* 线程安全
* */
public class SafeAdderServlet {
    private AtomicLong count = new AtomicLong(0);

    public void service(Request request, Response response){
        int value = request.getValue();
        System.out.println("Init Value: " + value);
        value += 6;
        System.out.println("Modified Value: " + value);
        response.setValue(value);
        count.incrementAndGet();
    }

    public long getCount(){
        return count.get();
    }

    public static void main(String[] args) {
        Request req = new Request(11);
        Response resp = new Response();
        SafeAdderServlet servlet = new SafeAdderServlet();
        for(int i = 0; i < 500000; i++){
            new Thread(() -> {
                servlet.service(req,resp);
                System.out.println(servlet.getCount());
            }).start();
        }
    }
}
