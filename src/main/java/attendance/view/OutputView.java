package attendance.view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    public static void printNotOperationDate(final LocalDate attendanceDate) {
        int month = attendanceDate.getMonthValue();
        int date = attendanceDate.getDayOfMonth();
        DayOfWeek day = attendanceDate.getDayOfWeek();
        String dayName = day.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.println("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.".formatted(month, date, dayName));
    }
}
