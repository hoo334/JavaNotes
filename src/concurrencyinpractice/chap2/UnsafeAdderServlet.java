package concurrencyinpractice.chap2;

/*
 * 2-2 在没有同步的情况下统计已请求数量的 Servlet
 * 非线程安全
 * */
public class UnsafeAdderServlet {
    private long count;

    public void service(Request request, Response response){
        int value = request.getValue();
        System.out.println("Init Value: " + value);
        value += 6;
        System.out.println("Modified Value: " + value);
        response.setValue(value);
        ++count;
    }

    public long getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        Request req = new Request(11);
        Response resp = new Response();
        UnsafeAdderServlet servlet = new UnsafeAdderServlet();
        for(int i = 0; i < 200000; i++){
            new Thread(() -> {
                servlet.service(req,resp);
                System.out.println(servlet.getCount());
            }).start();
        }
    }
}
