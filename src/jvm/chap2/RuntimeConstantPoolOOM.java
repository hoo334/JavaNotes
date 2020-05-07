package jvm.chap2;

import java.util.HashSet;
import java.util.Set;
/*
* 运行时常量池导致的OOM
* -XX:PermSize=6m -XX:MaxPermSize=6m 限制永久代的大小为6m <jdk 7
* -Xmx=6m >jdk 7
* */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        int i=0;
        while(true){
            //String.intern 方法会将String对象包含的字符串添加到常量池中
            set.add(String.valueOf(i++).intern());
        }
    }
}
