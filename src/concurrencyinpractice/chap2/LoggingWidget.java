package concurrencyinpractice.chap2;
/*
* 2-7 如果内置锁是不可重入的，这段代码会发生死锁
* 线程安全
* */
class Widget{
    public synchronized void doSomething(){
        System.out.println("Widget::doSomething()");
    }
}
public class LoggingWidget extends Widget{
    @Override
    public synchronized void doSomething() {
        System.out.println("LoggingWidget::doSomething()");
        super.doSomething();
    }

    public static void main(String[] args) {
        LoggingWidget widget = new LoggingWidget();
        widget.doSomething();
    }
}
