package concurrencyinpractice.chap2;
/*
* 2-3 延迟初始化中的竞态条件
* 非线程安全
* */
public class LazyInitRace {
    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() throws InterruptedException {
        if(instance == null){
            instance = new ExpensiveObject();
        }
        return instance;
    }

    public static void main(String[] args) {
        LazyInitRace lazyInitRace = new LazyInitRace();

        for(int i = 0; i < 3; i++){
            new Thread(() ->{
                try {
                    System.out.println(lazyInitRace.getInstance());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

class ExpensiveObject{
    public ExpensiveObject() throws InterruptedException {
        //假设创建对象时间为 200 ms，增加错误几率
        Thread.sleep(200);
    }
}