package view;

import domain.AbsentPolicy;
import domain.AttendanceDateTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

public class OutputView {

    public static void printAttendanceSheetIntro(String nickname) {
        System.out.print(System.lineSeparator());
        System.out.printf(ViewMessage.CURRENT_MONTH_ATTENDANCE_SHEET, nickname);
    }

    public static void printAttendanceSheets(Map<Integer, AttendanceDateTime> dayToAttendanceDateTime, int todayDate) {

        for (int day = 1; day < todayDate; day++) {
            LocalDate date = LocalDate.of(2024, 12, day);
            printAttendanceSheet(dayToAttendanceDateTime, date);
        }

        System.out.print(System.lineSeparator());
    }

    private static void printAttendanceSheet(Map<Integer, AttendanceDateTime> dayToAttendanceDateTime, LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return;
        }

        String koreanDayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        if (dayToAttendanceDateTime.containsKey(date.getDayOfMonth())) {
            AttendanceDateTime attendanceDateTime = dayToAttendanceDateTime.get(date.getDayOfMonth());
            LocalDateTime dateTime = attendanceDateTime.getAttendanceDateTime();

            System.out.printf(ViewMessage.ATTENDANCE_FORMAT, date.getDayOfMonth(), koreanDayOfWeek, dateTime.getHour(),
                    dateTime.getMinute(), attendanceDateTime.check().description);
            return;
        }
        System.out.printf(ViewMessage.ABSENT_FORMAT, date.getDayOfMonth(), koreanDayOfWeek);
    }

    public static void printAttendanceStatistics(int attendCount, int latCount, int absentCount) {

        System.out.printf(ViewMessage.STATISTICS_FORMAT, attendCount, latCount, absentCount);
    }

    public static void printAbsentPolicy(AbsentPolicy absentPolicy) {
        if (absentPolicy == AbsentPolicy.NONE) {
            return;
        }
        System.out.printf(ViewMessage.ABSENT_POLICY_FORMAT, absentPolicy.description);
    }

    public static void printRiskOfExpulsionBanner() {
        System.out.println(ViewMessage.RISK_OF_EXPULSION_BANNER);
    }

    public static void printRiskOfExpulsion(String name, int lateCount, int absentCount, AbsentPolicy absentPolicy) {
        System.out.printf(ViewMessage.RISK_OF_EXPULSION_FORMAT, name, absentCount, lateCount, absentPolicy.description);
    }
}
