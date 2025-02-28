package view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    public void displayAttendanceCheck(LocalDate nowDate, LocalTime attendTime, String status) {
        String dayOfWeek = nowDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

        System.out.printf("%d월 %02d일 %s ", nowDate.getMonthValue(), nowDate.getDayOfMonth(), dayOfWeek);
        System.out.printf("%02d:%02d (%s)%n", attendTime.getHour(), attendTime.getMinute(), status);
    }
}
