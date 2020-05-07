package corejava.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
* EchoServer多线程版本支持多个客户端
* */
public class ThreadedEchoServer {
    public static void main(String[] args) throws IOException{
        try(ServerSocket s= new ServerSocket(8189)){
            int i = 1;
            while(true){
                //accept方法等待连接，该方法阻塞直到当前线程建立连接为止，返回一个socke对象。
                Socket incoming = s.accept();
                System.out.println("Spawning "+i);
                Runnable r = new ThreadEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
        }
    }
}

class ThreadEchoHandler implements Runnable{
    private Socket incoming;

    public ThreadEchoHandler(Socket incoming) {
        this.incoming = incoming;
    }

    @Override
    public void run() {
        try(InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream()){
            Scanner in = new Scanner(inStream,"UTF-8");
            PrintWriter out= new PrintWriter(new OutputStreamWriter(outStream,"UTF-8"),true);

            out.println("Hello! Enter BYE to exit. ");
            //echo client input
            boolean done = false;
            while(!done && in.hasNextLine()){
                String line = in.nextLine();
               out.println("Echo: "+line);
                if(line.trim().equals("BYE"))done = true;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
