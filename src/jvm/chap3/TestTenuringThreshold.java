package jvm.chap3;

public class TestTenuringThreshold {
    private static final int _1MB = 1024*1024;
    /*
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
     * */

    //-XX:MaxTenuringThreshold=1 决定对象晋升老年代的阈值
    public static void testTenuringThreshold(){
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];

    }

    public static void main(String[] args) {
        testTenuringThreshold();
    }
}
