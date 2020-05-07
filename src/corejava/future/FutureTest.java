package corejava.future;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

//计算与关键字匹配的文件数目
/*
* Runnable 封装一个异步运行的任务可以想象为一个没有参数和返回值的异步方法。
* Callable与Runnable 类似，但是有返回值。Callable接口是一个参数化的类型，只有一个call方法。
* Callable<Integer>表示一个最终返回Integer对象的异步计算，Future保存异步计算的结果。可以启动一个计算，
* 将Future对象交给某个线程，然后忘掉它。Future对象的所有者在结果计算之后就可以获得它。
* */
public class FutureTest {
    public static void main(String[] args) {
        try(Scanner in = new Scanner(System.in)){
            System.out.println("Enter base directory(e.g. D:/doc):");
            String directory = in.nextLine();
            System.out.println("Enter keyword (e.g. txt):");
            String keyword = in.nextLine();

            MatchCounter counter = new MatchCounter(new File(directory),keyword);
            //FutureTask包装器是一种非常便利的机制可以将Callable转换成Future和Runnable，它同时实现两者的接口。
            //将counter转换为Runnable
            FutureTask<Integer> task = new FutureTask<>(counter);
            Thread t = new Thread(task);
            t.start();

            try {
                System.out.println(task.get() +" matching files.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}

class MatchCounter implements Callable<Integer>{
    private File directory;
    private String keyword;

    public MatchCounter(File directory, String keyword) {
        this.directory = directory;
        this.keyword = keyword;
    }

    @Override
    public Integer call() throws Exception {
        int count = 0;
        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for (File file : files)
                if(file.isDirectory()){
                    MatchCounter counter = new MatchCounter(file,keyword);
                    //将counter转换为Future
                    FutureTask<Integer> task = new FutureTask<>(counter);
                    //将Future添加到结果集
                    results.add(task);
                    //task 也是Runnable
                    Thread t = new Thread(task);
                    t.start();
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