package view;

import java.time.LocalDateTime;

import dto.AttendanceResult;
import dto.Formatter;

public class OutputView {
    public static void printAttendanceResult(AttendanceResult result) {
        System.out.println(LocalDateTime.of(result.date(), result.time()).format(Formatter.DATETIME_FORMATTER));
    }
}
