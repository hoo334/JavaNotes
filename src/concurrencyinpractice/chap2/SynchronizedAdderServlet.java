package concurrencyinpractice.chap2;

/*
* 2-6 能正确地缓存最新的计算结果，但并发性非常糟糕（不要这么做）
* 线程安全
* */
public class SynchronizedAdderServlet {
    private Integer lastNumber;
    private Integer lastResult;

    public synchronized void service(Request request, Response response){
        print();
        if(request.equals(lastNumber)){
            response.setValue(lastNumber);
        }else{
            lastNumber = request.getValue();
            response.setValue(request.getValue() + 6);
            lastResult = response.getValue();
        }
    }

    public void print(){
        System.out.println("lastNumber: " + lastNumber + "\t lastResult: " + lastResult);
    }

    public static void main(String[] args) {
        SynchronizedAdderServlet servlet = new SynchronizedAdderServlet();
        Response response = new Response();
        for(int i = 0; i < 50; i++){
            new Thread(() -> {
                servlet.service(new Request((int)(Math.random() * 100)), response);
            }).start();
        }
    }
}
