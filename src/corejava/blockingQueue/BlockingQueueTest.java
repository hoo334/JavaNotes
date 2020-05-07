package corejava.blockingQueue;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
* 使用阻塞队列来控制一组线程。程序在一个目录及它的所有子目录下搜索所有文件，打印出包含指定关键字的行。
* */
public class BlockingQueueTest {

    public static final int FILE_QUEUE_SIZE = 10;
    public static final int SEARCH_THREADS = 100;
    public static final File DUMMY = new File("");
    public static BlockingQueue<File> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    public static void main(String[] args) {

        try(Scanner in = new Scanner(System.in)){
            System.out.println("Enter base directory(e.g. D:/doc):");
            String directory = in.nextLine();
            System.out.println("Enter keyword (e.g. txt):");
            String keyword = in.nextLine();

            Runnable enumrator = ()->{
                try {
                   enumrate(new File(directory));//将所有文件加入阻塞队列中
                    queue.put(DUMMY);//Dummy代表文件结束
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };

            new Thread(enumrator).start();
            //搜索线程
            for (int i = 0; i < SEARCH_THREADS; i++) {
                Runnable searcher = ()->{
                    try {
                        boolean done = false;
                        while(!done){
                            File file = queue.take();//移除并返回头元素
                            if(file == DUMMY){
                                queue.put(file);
                                done = true;
                            }
                            else search(file,keyword);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };
                new Thread(searcher).start();
            }
        }
    }

    //Recursively enumrates all files in a given directory and its rectories.
    public static void enumrate(File directory) throws InterruptedException{
        File[] files = directory.listFiles();
        for (File file : files) {
            //如果是个路径继续枚举路径内的文件，并加入队列
            if(file.isDirectory())enumrate(file);
            else queue.put(file);
        }
    }

    //Searches a file for a given keyword and print all matching lines.
    public static void search(File file,String keyword) throws IOException{
        try(Scanner in = new Scanner(file,"UTF-8")){
            int lineNumber = 0;
            while(in.hasNextLine()){
                lineNumber++;
                String line = in.nextLine();
                if(line.contains(keyword))
                    System.out.printf("%s:%d:%s%n",file.getPath(),lineNumber,line);
            }
        }
    }
}

