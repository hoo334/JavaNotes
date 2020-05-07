package corejava.international;

import java.awt.*;

public class RetireResources_de extends java.util.ListResourceBundle{
    private static final Object[][] contents = {
            //BEGIN LOCALIZE
            {"colorPre", Color.yellow},{"colorGain",Color.black},{"colorLoss",Color.red}
            //END LOCALIZE
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }
}
