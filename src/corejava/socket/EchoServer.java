package corejava.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//命令行输入 telnet localhost 8189 连接至服务器
public class EchoServer {
    public static void main(String[] args) throws IOException{
        //establish server socket
        try(ServerSocket s = new ServerSocket(8189)){
            //wait for client connection
            try(Socket incoming = s.accept()){
                //得到客户端的输入
                InputStream inStream = incoming.getInputStream();
                //得到服务器到客户端的输出流
                OutputStream outStream = incoming.getOutputStream();
                //将流转换为Scanner
                try(Scanner in = new Scanner(inStream,"UTF-8")){
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream,"UTF-8"),true);
                    out.println("Hello! Enter BYE to exit");

                    //echo client input
                    boolean done = false;
                    //输入一行回显一行，输入BYE退出
                    while(!done && in.hasNextLine()){
                        String line = in.nextLine();
                        out.println("Echo: "+line);
                        if(line.trim().equals("BYE")) done = true;
                    }
                }
            }
        }
    }
}
