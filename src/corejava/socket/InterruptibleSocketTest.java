package corejava.socket;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/*
* 这个程序对比了可中断套接字和可阻塞套接字
* 数10个数字 可人为取消也可以等待数完服务器关闭
* */
public class InterruptibleSocketTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new InterruptibleSocketFrame();
            frame.setTitle("InterruptibleSocketTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class InterruptibleSocketFrame extends JFrame{
    private Scanner in;
    private JButton interruptibleButton;
    private JButton blockingButton;
    private JButton cancelButton;
    private JTextArea messages;
    private TestServer server;
    private Thread connectThread;
    public InterruptibleSocketFrame(){
        JPanel northPanel = new JPanel();
        add(northPanel,BorderLayout.NORTH);

        final int TEXT_ROWS = 20;
        final int TEXT_COLUMNS = 60;
        messages = new JTextArea(TEXT_ROWS,TEXT_COLUMNS);
        add(new JScrollPane(messages));

        interruptibleButton = new JButton("Interruptible");
        blockingButton = new JButton("Blocking");
        northPanel.add(interruptibleButton);
        northPanel.add(blockingButton);

        interruptibleButton.addActionListener(event ->{
            interruptibleButton.setEnabled(false);
            blockingButton.setEnabled(false);
            cancelButton.setEnabled(true);
            connectThread = new Thread(()->{
               try{
                   connectInterruptibly();
               }
               catch(IOException e){
                   messages.append("\nInterruptibleSocketTest.connectInterruptibly: "+e);
               }
            });
            connectThread.start();
        });

        blockingButton.addActionListener(event->{
            interruptibleButton.setEnabled(false);
            blockingButton.setEnabled(false);
            cancelButton.setEnabled(true);
            connectThread = new Thread(()->{
                try{
                    connectBlocking();
                }
                catch(IOException e){
                    messages.append("\nInterruptibleSocketTest.connectBlocking: "+e);
                }
            });
            connectThread.start();
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);
        northPanel.add(cancelButton);
        cancelButton.addActionListener(event->{
            connectThread.interrupt();//中断线程
            cancelButton.setEnabled(false);
        });

        //新建一个服务器线程
        server = new TestServer();
        new Thread(server).start();
        pack();
    }

    public void connectInterruptibly() throws IOException{
        messages.append("Interruptible:\n");
        try(SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost",8189))){
            //将通道转换为输入流
            in = new Scanner(channel,"UTF-8");
            while(!Thread.currentThread().isInterrupted()){
                messages.append("Reading ");
                if(in.hasNextLine()){
                    String line = in.nextLine();
                    messages.append(line);
                    messages.append("\n");
                }
            }
        }
        finally {
            //刷新显示
            EventQueue.invokeLater(()->{
                messages.append("Channel closed\n");
                interruptibleButton.setEnabled(true);
                blockingButton.setEnabled(true);
            });
        }
    }

    public void connectBlocking() throws IOException{
        messages.append("Blocking\n");
        try(Socket sock = new Socket("localhost",8189)){
            in = new Scanner(sock.getInputStream(),"UTF-8");
            while(!Thread.currentThread().isInterrupted()){
                messages.append("Reading ");
                if(in.hasNextLine()){
                    String line = in.nextLine();
                    messages.append(line);
                    messages.append("\n");
                }
            }
        }
        finally {
            //刷新显示
            EventQueue.invokeLater(()->{
                messages.append("Socket closed\n");
                interruptibleButton.setEnabled(true);
                blockingButton.setEnabled(true);
            });
        }
    }

    class TestServer implements Runnable{
        @Override
        public void run() {
            try(ServerSocket s = new ServerSocket(8189)){
                while(true){
                    Socket incoming = s.accept();
                    Runnable r = new TestServerHandler(incoming);
                    Thread t = new Thread(r);
                    t.start();
                }
            }catch (IOException e){
                messages.append("\nTestServer.run: "+e);
            }
        }
    }

    class TestServerHandler implements Runnable{
        private Socket incoming;
        private int counter;

        public TestServerHandler(Socket incoming) {
            this.incoming = incoming;
        }

        @Override
        public void run() {
            try{
                try{
                    OutputStream os = incoming.getOutputStream();
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(os,"UTF-8"),true);
                    while(counter < 100){
                        counter++;
                        if(counter <= 10)out.println(counter);
                        Thread.sleep(100);
                    }
                }
                finally {
                    incoming.close();
                    //Thread.currentThread().interrupt();
                    messages.append("Closing server\n");
                }
            }catch (Exception e){
                messages.append("\nTestServerHandler.run: "+e);
            }
        }
    }
}

