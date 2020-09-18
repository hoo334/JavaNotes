package jvm.chap10;

/**
 * 命名检查注解处理器测试类
 * 在 src目录下运行：
 * javac -encoding utf8 jvm/chap10/NameChecker.java jvm/chap10/NameCheckProcessor.java
 * javac -encoding utf8 -processor jvm.chap10.NameCheckProcessor jvm/chap10/BADLY_NAMED_CODE.java
 * 可看到相应的警告信息，证明注解处理器成功执行
 * */
public class BADLY_NAMED_CODE {
    enum colors{
        red, blue, green
    }

    static final int _FORTY_TWO = 42;

    public static int NOT_A_CONStANT = _FORTY_TWO;

    protected void BADLY_NAMED_CODE(){
        return;
    }

    public void NOTcamelCASEmethodName(){
        return;
    }
}
