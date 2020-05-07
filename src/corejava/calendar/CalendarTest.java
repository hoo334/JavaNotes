package corejava.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;

/*
 * @time 2019-12-25
 * @author liminghu
 *  */

public class CalendarTest {
    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int today = date.getDayOfMonth();

        date = date.minusDays(today - 1);//set to the start of month
        DayOfWeek weekday = date.getDayOfWeek();
        int value = weekday.getValue();//1 = Monday
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");
        for (int i = 1; i < value; i++)//print the blanks before the first day
            System.out.print("    ");
        while (date.getMonthValue() == month) {
            System.out.printf("%3d", date.getDayOfMonth());
            if (date.getDayOfMonth() == today)// symbol '*' represents today
                System.out.print('*');
            else
                System.out.print(' ');
            date = date.plusDays(1);
            //if the next day is Mon then newline
            if (date.getDayOfWeek().getValue() == 1) System.out.println();
        }
        if (date.getDayOfWeek().getValue() != 1) System.out.println();
    }
}
