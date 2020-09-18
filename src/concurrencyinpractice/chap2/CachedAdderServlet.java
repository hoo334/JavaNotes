package concurrencyinpractice.chap2;


/*
* 2-8 缓存最近计算的数值积计算结果的Servlet
* 线程安全
* */
public class CachedAdderServlet {
    private Integer lastNumber;
    private Integer lastResult;
    private long hits;
    private long cacheHits;

    public synchronized long getHits(){
        return hits;
    }

    public synchronized double getCacheHitRatio() {
        return (double)cacheHits / (double)hits;
    }

    public void service(Request request, Response response){
        //不要把从 request 提取数值等耗时操作放在同步代码块中
        int num = request.getValue();
        int result = Integer.MIN_VALUE;

        //判断是否命中缓存
        synchronized (this){
            ++hits;
            if(lastNumber != null && num == lastNumber){
                ++cacheHits;
                result = lastResult;
            }
        }
        //没有命中缓存就更新缓存的值
        if(result == Integer.MIN_VALUE){
            //计算结果
            result = request.getValue() + 6;
            synchronized (this){//更新缓存
                lastNumber = request.getValue();
                lastResult = result;
            }
        }
        response.setValue(result);
    }

    public static void main(String[] args) {
        CachedAdderServlet servlet = new CachedAdderServlet();
        Response response = new Response();
        for(int i = 0; i < 10; i++){
            new Thread(() -> {
                servlet.service(new Request((int)(Math.random() * 4)), response);
                System.out.println("Cache Hit Ratio: " + servlet.getCacheHitRatio());
            }).start();
        }

    }
}
