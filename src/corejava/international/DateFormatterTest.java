package corejava.international;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class DateFormatterTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new DateTimeFormatterFrame();
            frame.setTitle("DateTimeFormatterTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class DateTimeFormatterFrame extends JFrame{
private Locale[] locales;
private LocalDate currentDate;
private LocalTime currentTime;
private ZonedDateTime currentDateTime;
private DateTimeFormatter currentDateFormat;
private DateTimeFormatter currentTimeFormat;
private DateTimeFormatter currentDateTimeFormat;
private JComboBox<String> localeCombo = new JComboBox<>();
private JButton dateParseButton = new JButton("Parse");
private JButton timeParseButton = new JButton("Parse");
private JButton dateTimeParseButton = new JButton("Parse");
private JTextField dateText = new JTextField(30);
private JTextField timeText = new JTextField(30);
private JTextField dateTimeText = new JTextField(30);
private EnumCombo<FormatStyle> dateStyleCombo = new EnumCombo<>(FormatStyle.class,
        "Short","Medium","Long","Full");
private EnumCombo<FormatStyle> timeStyleCombo = new EnumCombo<>(FormatStyle.class,
            "Short","Medium","Long","Full");
private EnumCombo<FormatStyle> dateTimeStyleCombo = new EnumCombo<>(FormatStyle.class,
            "Short","Medium","Long","Full");

public DateTimeFormatterFrame(){
    //
    setLayout(new GridBagLayout());
    add(new Label("Locale"),new GBC(0,0).setAnchor(GBC.EAST));
    add(localeCombo,new GBC(1,0,2,1).setAnchor(GBC.WEST));

    add(new JLabel("Date"),new GBC(0,1).setAnchor(GBC.EAST));
    add(dateStyleCombo,new GBC(1,1).setAnchor(GBC.WEST));
    add(dateText,new GBC(2,1,2,1).setFill(GBC.HORIZONTAL));
    add(dateParseButton,new GBC(4,1).setAnchor(GBC.WEST));

    add(new JLabel("Time"),new GBC(0,2).setAnchor(GBC.EAST));
    add(timeStyleCombo,new GBC(1,2).setAnchor(GBC.WEST));
    add(timeText,new GBC(2,2,2,1).setFill(GBC.HORIZONTAL));
    add(timeParseButton,new GBC(4,2).setAnchor(GBC.WEST));

    add(new JLabel("Date and Time"),new GBC(0,3).setAnchor(GBC.EAST));
    add(dateTimeStyleCombo,new GBC(1,3).setAnchor(GBC.WEST));
    add(dateTimeText,new GBC(2,3,2,1).setFill(GBC.HORIZONTAL));
    add(dateTimeParseButton,new GBC(4,3).setAnchor(GBC.WEST));

    //存储所有locale 并使用名字排序，设定当前locale为显示语言
    locales = (Locale[]) NumberFormat.getAvailableLocales().clone();
    Arrays.sort(locales,Comparator.comparing(Locale::getDisplayName));
    for (Locale loc : locales) {
        localeCombo.addItem(loc.getDisplayName());
    }
    localeCombo.setSelectedItem(Locale.getDefault().getDisplayName());
    currentDate = LocalDate.now();
    currentTime = LocalTime.now();
    currentDateTime = ZonedDateTime.now();
    updateDisplay();

    ActionListener listener = event->updateDisplay();

    localeCombo.addActionListener(listener);
    dateStyleCombo.addActionListener(listener);
    timeStyleCombo.addActionListener(listener);
    dateTimeStyleCombo.addActionListener(listener);

    //覆盖actionPerformed方法
    dateParseButton.addActionListener(event ->{
        String d = dateText.getText().trim();
        try {
            currentDate = LocalDate.parse(d,currentDateFormat);
            updateDisplay();
        } catch (Exception e) {
            dateText.setText(e.getMessage());
        }
    });

    timeParseButton.addActionListener(event ->{
        String t = timeText.getText().trim();
        try {
            currentTime = LocalTime.parse(t,currentTimeFormat);
            updateDisplay();
        } catch (Exception e) {
            timeText.setText(e.getMessage());
        }
    });

    dateTimeParseButton.addActionListener(event ->{
        String t = dateTimeText.getText().trim();
        try {
            currentDateTime = ZonedDateTime.parse(t,currentDateTimeFormat);
            updateDisplay();
        } catch (Exception e) {
            dateTimeText.setText(e.getMessage());
        }
    });

    pack();
}
    //更新信息
    public void updateDisplay(){
        Locale currentLocale = locales[localeCombo.getSelectedIndex()];

        FormatStyle dateStyle = dateStyleCombo.getValue();
        currentDateFormat = DateTimeFormatter.ofLocalizedDate(dateStyle).withLocale(currentLocale);
        dateText.setText(currentDateFormat.format(currentDate));

        FormatStyle timeStyle = timeStyleCombo.getValue();
        currentTimeFormat = DateTimeFormatter.ofLocalizedTime(timeStyle).withLocale(currentLocale);
        timeText.setText(currentTimeFormat.format(currentTime));

        FormatStyle dateTimeStyle = dateTimeStyleCombo.getValue();
        currentDateTimeFormat = DateTimeFormatter.ofLocalizedDateTime(dateTimeStyle).withLocale(currentLocale);
        dateTimeText.setText(currentDateTimeFormat.format(currentDateTime));
    }
}