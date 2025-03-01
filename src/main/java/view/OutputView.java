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
    public static final DateTimeFormatter ABSENCE_FORMATTER =
        DateTimeFormatter.ofPattern("MM월 dd일 EEE요일 --:--");

    public static void printException(IllegalArgumentException e) {
        System.out.println("[ERROR] " + e.getMessage());
        blankLine();
    }

    public static void printAttendResult(AttendanceDateTime result) {
        String formatted = formatAttendanceDateTime(result);
        System.out.println(formatted);
        blankLine();
    }

    public static void printModifyResult(AttendanceDateTime before, AttendanceDateTime after) {
        String formattedBefore = formatAttendanceDateTime(before);
        String formattedAfter = formatAttendanceDateTime(after);
        System.out.printf("%s -> %s 수정 완료!%n", formattedBefore, formattedAfter);
        blankLine();
    }

    private static String formatAttendanceDateTime(AttendanceDateTime attendanceDateTime) {
        AttendanceStatus status = attendanceDateTime.getAttendanceStatus();
        LocalDate date = attendanceDateTime.getDate();
        LocalTime time = attendanceDateTime.getTime();

        if (time == null) {
            return String.format("%s (%s)", ABSENCE_FORMATTER.format(date), status);
        }
        return String.format("%s (%s)",
            DATE_TIME_FORMATTER.format(LocalDateTime.of(date, time)), status);
    }

    private static void blankLine() {
        System.out.println();
    }
}
