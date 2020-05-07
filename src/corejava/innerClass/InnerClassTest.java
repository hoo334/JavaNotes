package corejava.innerClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class InnerClassTest {
    public static void main(String[] args){
        TalkingClock clock = new TalkingClock(1000,true);
        clock.start();;
    }
}

class TalkingClock{
    private int interval;
    private boolean beep;

    public TalkingClock(int interval,boolean beep){
        this.interval = interval;
        this.beep = beep;
    }

    public void start(){
        ActionListener listener = new TimePrinter();
        Timer t=  new Timer(interval,listener);
        t.start();
    }

    public class TimePrinter implements ActionListener{
        public void actionPerformed(ActionEvent event){
            System.out.println("At the tone,the time is "+new Date() );
            //内部类可以直接访问外围类的域，只有内部类可以是私有类，常规类有包可见性，或共有可见性。
            if(beep)Toolkit.getDefaultToolkit().beep();
        }
    }

}

