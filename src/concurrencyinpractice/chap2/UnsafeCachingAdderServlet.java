package concurrencyinpractice.chap2;

import java.util.concurrent.atomic.AtomicReference;
/*
* 2-5 在没有足够原子性保证的情况下对最近计算结果进行缓存
* 非线程安全
* */
public class UnsafeCachingAdderServlet {
    private final AtomicReference<Integer> lastNumber = new AtomicReference<>();
    private final AtomicReference<Integer> lastResult = new AtomicReference<>();

    public void service(Request request, Response response) throws InterruptedException {
        print();
        if(request.equals(lastNumber.get())){
            response.setValue(lastResult.get());
        }else{
            response.setValue(request.getValue() + 6);
            lastNumber.set(request.getValue());
            Thread.sleep(3);
            lastResult.set(response.getValue());
        }
    }

    public void print(){
            System.out.println("lastNumber: " + lastNumber + "\t lastResult: " + lastResult);
    }

    public static void main(String[] args) {
        UnsafeCachingAdderServlet servlet = new UnsafeCachingAdderServlet();
        Response response = new Response();
        for(int i = 0; i < 50; i++){
            new Thread(() -> {
                try {
                    servlet.service(new Request((int)(Math.random() * 100)), response);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
