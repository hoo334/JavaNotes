package corejava.threadPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/*
* 如果程序中创建了大量的生命周期很短的线程，应该使用线程池，将Runnable对象交给线程池，就会有一个线程调用run方法，
* 当run方法退出时，线程不会死亡，而是在池中准备为下一个请求提供服务。
* Executor类静态工厂方法：
* 1、newCachedThreadPool 必要时创建新线程，空闲线程保留60s。
* 2、newFixedThreadPool 包含固定数量的线程，空闲线程一直保留。
* 3、newSingleThreadExecutor 只有一个线程的“池”，顺序执行每一个提交的任务。
* 4、newScheduledThreadPool 用于预定执行而构建的固定线程池代替 java.util.Timer。
* 5、newSingleThreadScheduledExecutor 用于预定执行而构建的单线程“池”。
* */

//统计出现关键字的文件个数
public class ThreadPoolTest {
    public static void main(String[] args) {
        try(Scanner in = new Scanner(System.in)){
            System.out.println("Enter base directory(e.g. D:/doc):");
            String directory = in.nextLine();
            System.out.println("Enter keyword (e.g. txt):");
            String keyword = in.nextLine();
            //新建一个线程池
           ExecutorService pool = Executors.newCachedThreadPool();

           MatchCounter counter  = new MatchCounter(new File(directory),keyword,pool);
           //将Runnable对象提交到线程池。
            Future<Integer> result = pool.submit(counter);

            try {
                System.out.println(result.get() +" matching files.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //关闭线程池。
            pool.shutdown();
            int largePoolSize = ( (ThreadPoolExecutor)pool ).getLargestPoolSize();
            System.out.println("largest pool size= "+largePoolSize);
        }
    }
}


class MatchCounter implements Callable<Integer>{
    private File directory;
    private String keyword;
    private ExecutorService pool;
    private int count;

    public MatchCounter(File directory, String keyword, ExecutorService pool) {
        this.directory = directory;
        this.keyword = keyword;
        this.pool = pool;
    }

    @Override
    public Integer call() throws Exception {
        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for (File file : files)
                if(file.isDirectory()){
                   MatchCounter counter = new MatchCounter(file,keyword,pool);
                   //将任务提交到线程池
                   Future<Integer> result = pool.submit(counter);
                   //将计算结果添加到结果集。
                   results.add(result);
                }
                else
                {
                    if(search(file))count++;
                }
            for(Future<Integer> result : results)
                try {
                    count += result.get();//统计所有文件个数
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return count;
    }

    public boolean search(File file){
        try {
            try(Scanner in = new Scanner(file,"UTF-8")){
                boolean found = false;
                while(!found && in.hasNextLine()){
                    String line = in.nextLine();
                    if(line.contains(keyword))found = true;//检查文件是否包含关键字
                }
                return found;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}

