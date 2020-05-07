package corejava.timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;


public class TimeTest {
public static void main(String[] args){
    ActionListener listener = new TimePrinter();
    //建立一个计时器调用监听器
    Timer t = new Timer(3000,listener);
    t.start();
    JOptionPane.showMessageDialog(null,"Quit Program");
    System.exit(0);
}
}

class TimePrinter implements ActionListener{
    public void actionPerformed(ActionEvent event){
        System.out.println("At the tone ,the time is"+ new Date());
        Toolkit.getDefaultToolkit().beep();//发出一声铃响
    }
}