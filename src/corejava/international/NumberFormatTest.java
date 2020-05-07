package corejava.international;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class NumberFormatTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new NumberFormatFrame();
            frame.setTitle("NumebrFormatTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class NumberFormatFrame extends JFrame{
    private Locale[] locales;
    private double currentNumber;
    private JComboBox<String> localeCombo = new JComboBox<>();
    private JButton parseButton = new JButton("Prse");
    private JTextField numberText = new JTextField(30);
    private JRadioButton numberRadioButton = new JRadioButton("Number");
    private JRadioButton currencyRadioButton = new JRadioButton("Currency");
    private JRadioButton percentRadioButton = new JRadioButton("Percent");
    private ButtonGroup rbGroup = new ButtonGroup();
    private NumberFormat currentNumberFormat;

    public NumberFormatFrame(){
        //设定框架布局
        setLayout(new GridBagLayout());
        //添加监听
        ActionListener listener = event -> updateDisplay();

        JPanel p = new JPanel();
        addRadioButton(p,numberRadioButton,rbGroup,listener);
        addRadioButton(p,currencyRadioButton,rbGroup,listener);
        addRadioButton(p,percentRadioButton,rbGroup,listener);

        //添加控件
        add(new JLabel("Locale: "),new GBC(0,0).setAnchor(GBC.EAST));
        add(p,new GBC(1,1));
        add(parseButton,new GBC(0,2).setInsets(2));
        add(localeCombo,new GBC(1,0).setAnchor(GBC.WEST));
        add(numberText,new GBC(1,2).setFill(GBC.HORIZONTAL));

        //存储所有locale 并使用名字排序，设定当前locale为显示语言
        locales = (Locale[]) NumberFormat.getAvailableLocales().clone();
        Arrays.sort(locales,Comparator.comparing(Locale::getDisplayName));
        for (Locale loc : locales) {
            localeCombo.addItem(loc.getDisplayName());
        }
        localeCombo.setSelectedItem(Locale.getDefault().getDisplayName());
        currentNumber = 123456.78;
        updateDisplay();

        //当选择框改变重新显示界面
        localeCombo.addActionListener(listener);

        //添加解析按钮响应事件解析不了抛异常
        parseButton.addActionListener(event ->{
            try {
                String s = numberText.getText().trim();
                Number n = currentNumberFormat.parse(s);
                if(n != null){
                    currentNumber = n.doubleValue();
                    updateDisplay();
                }
                else{
                    numberText.setText("Parse Error: "+s);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        pack();
    }

    //添加选择按钮
    public void addRadioButton(Container p,JRadioButton b,ButtonGroup g, ActionListener listener){
        b.setSelected(g.getButtonCount() == 0);
        b.addActionListener(listener);
        g.add(b);
        p.add(b);
    }

    //判断当前选中的按钮并显示格式化后的字符
    public void updateDisplay(){
        Locale currentLocale = locales[localeCombo.getSelectedIndex()+1];
        currentNumberFormat = null;
        if(numberRadioButton.isSelected())
            currentNumberFormat = NumberFormat.getNumberInstance(currentLocale);
        else if(currencyRadioButton.isSelected())
            currentNumberFormat = NumberFormat.getCurrencyInstance(currentLocale);
        else if(percentRadioButton.isSelected())
            currentNumberFormat = NumberFormat.getPercentInstance(currentLocale);

        String formatted = currentNumberFormat.format(currentNumber);
        numberText.setText(formatted);
    }
}