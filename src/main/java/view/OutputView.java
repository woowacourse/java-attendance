package view;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import dto.AttendanceResult;
import dto.Formatter;

public class OutputView {
    public static void printAttendanceResult(AttendanceResult result) {
        System.out.printf(LocalDateTime.of(result.date(), result.time()).format(Formatter.DATETIME_FORMATTER) + "%n",
            result.date().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN));
    }
}
