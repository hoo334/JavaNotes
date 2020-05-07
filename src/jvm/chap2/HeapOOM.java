package jvm.chap2;

import java.util.ArrayList;
import java.util.List;

/*
* Java 堆内存溢出
* -Xms 20m -Xmx 20m 堆最大为 20 m
* */
public class HeapOOM {
    static class OOMObject{

    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();

        while(true){
            list.add(new OOMObject());
        }
    }
}
