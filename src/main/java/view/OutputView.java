package view;

import domain.AbsentPolicy;
import domain.AttendanceStatics;
import domain.AttendanceDate;
import domain.AttendanceDateTime;
import domain.AttendanceSheet;
import domain.AttendanceSheets;
import domain.AttendanceState;
import domain.AttendanceTime;
import domain.Calendar;
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

    public static void printUpdateInformation(AttendanceTime beforeAttendanceTime,
                                              AttendanceDateTime afterAttendanceDateTime,
                                              AttendanceState beforeState,
                                              AttendanceState afterState) {
        AttendanceTime afterDateTime = afterAttendanceDateTime.getAttendanceTime();

        System.out.print(System.lineSeparator());
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!%n",
                Calendar.DECEMBER.month,
                afterAttendanceDateTime.getAttendanceDate().getDayOfMonth(),
                afterAttendanceDateTime.getAttendanceDate().getDayOfWeek()
                        .getDisplayName(TextStyle.FULL, Locale.KOREAN),
                beforeAttendanceTime.getHour(), beforeAttendanceTime.getMinute(),
                beforeState.description,
                afterDateTime.getHour(), afterDateTime.getMinute(),
                afterState.description
        );
        System.out.print(System.lineSeparator());
    }

    public static void printAttendanceSheetsByCrew(String nickname, List<AttendanceSheet> attendancesByNickname,
                                                   AttendanceSheets attendanceSheets, LocalDate today) {
        AttendanceStatics attendanceStatics = attendanceSheets.calculateAttendanceStaticsBy(nickname, today);
        printAttendanceSheetIntro(nickname);
        printAttendanceSheets(attendancesByNickname, today.getDayOfMonth());
        printAttendanceStatistics(attendanceStatics);
        System.out.print(System.lineSeparator());
    }

    public static void printAttendanceSheetIntro(String nickname) {
        System.out.print(System.lineSeparator());
        System.out.printf(ViewMessage.CURRENT_MONTH_ATTENDANCE_SHEET, nickname);
    }

    public static void printAttendanceSheets(List<AttendanceSheet> attendanceSheets, int todayDate) {
        for (int day = Calendar.DECEMBER.startDay; day < todayDate; day++) {
            LocalDate date = LocalDate.of(2024, Calendar.DECEMBER.month, day);
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

    public static void printAttendanceStatistics(AttendanceStatics attendanceStatics) {
        System.out.printf(ViewMessage.STATISTICS_FORMAT, attendanceStatics.getAttendCount(),
                attendanceStatics.getLateCount(), attendanceStatics.getAbsentCount());
        printAbsentPolicy(attendanceStatics.getAbsentPolicy());
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

    public static void printRiskOfExpulsion(String name, AttendanceStatics attendanceStatics) {
        System.out.printf(ViewMessage.RISK_OF_EXPULSION_FORMAT, name, attendanceStatics.getAbsentCount(),
                attendanceStatics.getLateCount(), attendanceStatics.getAbsentPolicy().description);
    }
}
