package view;

import domain.AttendanceStatus;
import domain.dateTime.AttendanceDateTime;
import domain.record.AttendanceRecord;
import domain.record.AttendanceRecords;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import util.DateUtil;

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


    public static void printValidAttendances(final LocalDateTime localDateTime,
                                             final AttendanceRecords attendanceRecords) {
        final int endOfDay = localDateTime.getDayOfMonth() - 1;
        final int year = localDateTime.getYear();
        final int month = localDateTime.getMonthValue();
        final LocalDate date = LocalDate.of(year, month, endOfDay);
        final List<Integer> validDays = DateUtil.calculateValidDays(year, month, endOfDay);

        validDays.forEach(day -> {
            final DateTimeFormatter absenceFormat = DateTimeFormatter.ofPattern("M월 dd일 EEEE", Locale.KOREAN);
            final AttendanceRecord attendanceRecord = attendanceRecords.findByDate(LocalDate.of(year, month, day));

            if (attendanceRecord != null) {
                printAttendanceInRecord(attendanceRecord);
                return;
            }
            printF("%s --:-- (결석)%n", date.format(absenceFormat));
        });
    }

    private static void printAttendanceInRecord(final AttendanceRecord attendanceRecord) {
        final ResourceBundle bundle = ResourceBundle.getBundle("attendanceStatus");
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 dd일 EEEE HH:mm", Locale.KOREAN);
        final AttendanceDateTime attendanceDateTime = attendanceRecord.getAttendanceDateTime();
        final LocalDateTime dateTime = attendanceDateTime.getDateTime();
        final AttendanceStatus attendanceStatus = attendanceRecord.getAttendanceStatus();

        printF("%s (%s)%n", dateTime.format(formatter), bundle.getString(attendanceStatus.name()));
    }

    private static void printF(final String message, final Object... args) {
        System.out.printf(message, args);
    }
}
