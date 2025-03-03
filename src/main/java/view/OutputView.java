package view;

import domain.dateTime.AttendanceDateTime;
import domain.record.AttendanceRecord;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public final class OutputView {

    private OutputView() {
    }

    public static void printAttendanceCheck(final LocalDateTime time, final String attendanceStatus) {
        final ResourceBundle bundle = ResourceBundle.getBundle("attendanceStatus");
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 dd일 EEEE HH:mm", Locale.KOREAN);

        printF("%n %s (%s)%n", time.format(formatter), bundle.getString(attendanceStatus));
    }

    public static void printEditAttendanceDateTime(final AttendanceRecord beforeRecord,
                                                   final AttendanceRecord afterRecord) {
        final AttendanceDateTime beforeAttendanceDateTime = beforeRecord.getAttendanceDateTime();
        final LocalDateTime beforeDateTime = beforeAttendanceDateTime.getDateTime();
        final AttendanceDateTime afterAttendanceDateTime = afterRecord.getAttendanceDateTime();
        final LocalDateTime afterDateTime = afterAttendanceDateTime.getDateTime();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 dd일 EEEE HH:mm", Locale.KOREAN);
        final ResourceBundle bundle = ResourceBundle.getBundle("attendanceStatus");

        printF("%n %s (%s) -> %s (%s) 수정 완료!%n",
                beforeDateTime.format(formatter),
                bundle.getString(beforeRecord.getAttendanceStatus().name()),
                afterDateTime.toLocalTime(),
                bundle.getString(afterRecord.getAttendanceStatus().name())
        );
    }

    private static void printF(final String message, final Object... args) {
        System.out.printf(message, args);
    }
}
