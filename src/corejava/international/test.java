package corejava.international;

import java.util.Locale;
import java.util.ResourceBundle;

public class test {
    public static void main(String[] args) {
        Locale locales = Locale.GERMANY;
        ResourceBundle res;
        res= ResourceBundle.getBundle("com.hoo.international.RetireResources", locales);
        System.out.println(res.getString("language"));

    }
}
