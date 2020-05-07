package corejava.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class LocalDates {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        System.out.println("Today: "+today);

        LocalDate alonzosBirthday = LocalDate.of(1903,6,14);
        //Uses the Month enumeration
        alonzosBirthday = LocalDate.of(1903, Month.JUNE,14);
        System.out.println("alonzosBirthday: "+alonzosBirthday);

        LocalDate programmersDay = LocalDate.of(2020,1,1).plusDays(255);
        System.out.println("programmersDay: "+programmersDay);

        LocalDate PRCDay = LocalDate.of(2020,10,1);
        LocalDate christamas = LocalDate.of(2020, Month.DECEMBER,25);

        //国庆离圣诞节还有多少天
        System.out.println("Until christmas: "+PRCDay.until(christamas));
        System.out.println("Until christmas: "+PRCDay.until(christamas,ChronoUnit.DAYS));

        //加一个月减一个月可能会创建出不存在的日期，不会抛出异常，会返回该月有效的最后一天。
        System.out.println(LocalDate.of(2016,1,31).plusMonths(1));
        System.out.println(LocalDate.of(2016,1,31).minusMonths(1));

        //1990.1.1是星期几
        DayOfWeek startOfLastMillennium = LocalDate.of(1900,1,1).getDayOfWeek();
        System.out.println("startOfLastMillennium: "+startOfLastMillennium);
        //Monday = 1 Tuesday = 2......
        System.out.println(startOfLastMillennium.getValue());
        System.out.println(DayOfWeek.SATURDAY.plus(3));
    }
}
