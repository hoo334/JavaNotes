package jvm.chap2;

/*
* 创建线程导致虚拟机栈内存溢出
* -Xss2M
* */
public class JavaVMStackOOM {

    private void dontStop(){
        while(true){

        }
    }

    public void stackLeakByThread(){
        while(true){
            Thread t = new Thread(()->{
               dontStop();
            });
            t.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }
}
