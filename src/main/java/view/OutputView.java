package view;

import domain.AbsentPolicy;
import domain.AttendanceDateTime;
import domain.AttendanceSheet;
import domain.AttendanceTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public static void printAddInformation(int hour, int minute, AttendanceDateTime attendanceDateTime) {
        AttendanceDate attendanceDate = attendanceDateTime.getAttendanceDate();

        System.out.print(System.lineSeparator());
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s)%n",
                Calendar.DECEMBER.month,
                attendanceDate.getDayOfMonth(),
                attendanceDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                hour, minute,
                attendanceDateTime.check().description
        );
        System.out.print(System.lineSeparator());
    }

    public static void printAttendanceSheetsByCrew(String nickname, List<AttendanceSheet> attendancesByNickname,
                                                   AttendanceSheets attendanceSheets, LocalDate today) {
        int attendCount = attendanceSheets.calculateAttendCountBy(nickname);
        int lateCount = attendanceSheets.calculateLateCountBy(nickname);
        int absentCount = attendanceSheets.calculateAbsentCount(nickname, today);

        printAttendanceSheetIntro(nickname);
        printAttendanceSheets(attendancesByNickname, today.getDayOfMonth());
        printAttendanceStatistics(attendCount, lateCount, absentCount);

        printAbsentPolicy(AbsentPolicy.calculateAbsentPolicy(absentCount, lateCount));
        System.out.print(System.lineSeparator());
    }

    public static void printAttendanceSheetIntro(String nickname) {
        System.out.print(System.lineSeparator());
        System.out.printf(ViewMessage.CURRENT_MONTH_ATTENDANCE_SHEET, nickname);
    }

    public static void printAttendanceSheets(List<AttendanceSheet> attendanceSheets, int todayDate) {
        for (int day = 1; day < todayDate; day++) {
            LocalDate date = LocalDate.of(2024, 12, day);
            printAttendanceSheet(attendanceSheets, date);
        }

        System.out.print(System.lineSeparator());
    }

    private static void printAttendanceSheet(List<AttendanceSheet> attendanceSheets, LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return;
        }

        String koreanDayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        AttendanceSheet attendanceSheet = attendanceSheets.stream()
                .filter(sheet -> sheet.isSameDay(date.getDayOfMonth()))
                .findAny()
                .orElse(null);

        if (attendanceSheet == null) {
            System.out.printf(ViewMessage.ABSENT_FORMAT, date.getDayOfMonth(), koreanDayOfWeek);
            return;
        }

        AttendanceDateTime attendanceDateTime = attendanceSheet.getAttendanceDateTime();
        AttendanceTime attendanceTime = attendanceDateTime.getAttendanceTime();
        System.out.printf(ViewMessage.ATTENDANCE_FORMAT, date.getDayOfMonth(), koreanDayOfWeek,
                attendanceTime.getHour(),
                attendanceTime.getMinute(), attendanceDateTime.check().description);
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
