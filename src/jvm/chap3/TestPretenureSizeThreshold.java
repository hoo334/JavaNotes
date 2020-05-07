package jvm.chap3;

public class TestPretenureSizeThreshold {
    private static final int _1MB = 1024*1024;
    /*
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
     * -XX:PretenureSizeThreshold=314572 指定大于该值的对象直接在老年代分配，避免在 Eden 和两个 Survivor 之间来回复制。
     * -XX:PretenureSizeThreshold 只对 Serial 和 ParNew 两款新生代收集器有效。
     * */

    public static void testPretenureSizeThreshold(){
        byte[] allocation;
        allocation = new byte[4 * _1MB];//直接分配在老年代中
    }

    public static void main(String[] args) {
        testPretenureSizeThreshold();
    }
}
