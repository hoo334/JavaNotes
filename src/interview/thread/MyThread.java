package interview.thread;

import java.util.Date;

public class MyThread extends Thread{

    private int no;

    public MyThread(int no) {
        this.no = no;
    }

    @Override
    public void run() {
        super.run();
        System.out.println("Thread "+no+" is running.");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            MyThread t = new MyThread(i);
            t.start();
        }

        for (int i = 0; i < 20; i++) {
            Thread t = new Thread(()->{
                System.out.println(new Date());
            });
            t.start();
        }
    }
}


