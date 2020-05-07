package corejava.anonymousInnerClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AnonymousInnerClassTest {
    public static void main(String[] args){
        TalkingClock clock = new TalkingClock();
        clock.start(1000,true);
        JOptionPane.showMessageDialog(null,"Quit Program");
    }
}

class TalkingClock{
    public void start(int interval,boolean beep){
        ActionListener listener = new ActionListener() {
            //内部类方法和数据
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("At the tone,the time is "+ new Date());
                if(beep)Toolkit.getDefaultToolkit().beep();
            }
        };
        Timer t = new Timer(interval,listener);
        t.start();
    }
}
