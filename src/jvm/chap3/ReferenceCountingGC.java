package jvm.chap3;

public class ReferenceCountingGC {
    public Object instance = null;


    private static final int _1MB = 1024*1024;

    // 占点内存，以便在GC日志中看清楚是否有回收过
    private byte[] bigSize = new byte[_1MB];

    public static void main(String[] args) {
        ReferenceCountingGC obj1 = new ReferenceCountingGC();
        ReferenceCountingGC obj2 = new ReferenceCountingGC();

        obj1.instance = obj2;
        obj2.instance = obj1;

        obj1 = null;
        obj2 = null;
        System.gc();
    }
}
