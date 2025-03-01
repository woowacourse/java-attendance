package view;

import domain.AttendanceDateTime;
import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class OutputView {

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("MM월 dd일 EEE요일 HH:mm");

    public static void printException(IllegalArgumentException e) {
        System.out.println("[ERROR] " + e.getMessage());
    }

    public static void printAttendResult(AttendanceDateTime result) {
        LocalDate date = result.getDate();
        LocalTime time = result.getTime();
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        AttendanceStatus status = result.getAttendanceStatus();

        System.out.printf("%s (%s)%n", dateTime.format(DATE_TIME_FORMATTER), status);
    }
}
