package view;

import domain.Attendance;
import domain.AttendanceDateTime;
import domain.AttendanceStatus;
import domain.AttendanceSummary;
import domain.CrewSummary;
import domain.Punishment;
import domain.Week;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import util.Constants;

public final class OutputView {

    private static final int ABSENCE_HOUR = 0;

    private OutputView() {
    }

    public static void printAttendance(Attendance attendance) {
        final LocalDateTime localDateTime = attendance.getLocalDateTime();
        final AttendanceStatus attendanceStatus = attendance.getAttendanceStatus();
        final int day = localDateTime.getDayOfMonth();
        final DayOfWeek dayName = localDateTime.getDayOfWeek();
        final LocalTime localTime = localDateTime.toLocalTime();

        System.out.println(
                String.format("%d월 %02d일 %s %s (%s)", Constants.FIXED_MONTH, day, dayName, localTime,
                        attendanceStatus.getKoreanName()));
    }

    public static void printUpdateAttendance(final Attendance oldAttendance, final Attendance newAttendance) {
        final LocalDateTime oldLocalDateTime = oldAttendance.getLocalDateTime();
        final AttendanceStatus oldAttendanceStatus = oldAttendance.getAttendanceStatus();
        final int oldDay = oldLocalDateTime.getDayOfMonth();
        final DayOfWeek oldDayName = oldLocalDateTime.getDayOfWeek();
        final LocalTime oldLocalTime = oldLocalDateTime.toLocalTime();

        final LocalDateTime newLocalDateTime = newAttendance.getLocalDateTime();
        final AttendanceStatus newAttendanceStatus = newAttendance.getAttendanceStatus();
        final LocalTime newLocalTime = newLocalDateTime.toLocalTime();

        System.out.println(
                String.format("%d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!", Constants.FIXED_MONTH, oldDay, oldDayName,
                        oldLocalTime,
                        oldAttendanceStatus.getKoreanName(), newLocalTime, newAttendanceStatus.getKoreanName()));
    }

    public static void printCrewAttendances(final CrewSummary crewSummaries,
                                            List<AttendanceSummary> attendanceSummaries) {
        final String nickname = crewSummaries.nickname();
        final Punishment punishment = crewSummaries.punishment();
        final String titleFormat = "이번 달 %s의 출석 기록입니다.";

        printMessageWithLineSeparator(String.format(titleFormat, nickname));
        printAttendances(attendanceSummaries);
        printCountAboutAttendance(crewSummaries);
        printMessageWithLineSeparator(String.format("%s 대상자입니다.", punishment.getPunishmentName()));
    }

    private static void printAttendances(List<AttendanceSummary> attendanceSummaries) {
        for (AttendanceSummary attendanceSummary : attendanceSummaries) {
            final AttendanceDateTime attendanceDateTime = attendanceSummary.attendanceDateTime();
            String formattedDateTime = adjustFormat(attendanceDateTime.getLocalDateTime());
            printMessage(formattedDateTime);
        }
    }

    private static String adjustFormat(LocalDateTime localDateTime) {
        if (localDateTime.getHour() == ABSENCE_HOUR) {
            return localDateTime.format(Week.ABSENCE_FORMAT);
        }
        return localDateTime.format(Week.KOREAN_DATE_TIME_FORMAT);
    }

    private static void printCountAboutAttendance(final CrewSummary crewSummaries) {
        final String countFormmat = "%s: %d회";
        printMessage(
                String.format(countFormmat, AttendanceStatus.ATTENDANCE.getKoreanName(),
                        crewSummaries.attendanceCount()));
        printMessage(
                String.format(countFormmat, AttendanceStatus.TARDINESS.getKoreanName(),
                        crewSummaries.tardinessCount()));
        printMessage(
                String.format(countFormmat, AttendanceStatus.ABSENCE.getKoreanName(), crewSummaries.absenceCount()));
    }

    public static void printAllExpulsion(final List<CrewSummary> crewSummaries) {
        printMessageWithLineSeparator("제적 위험자 조회");

        for (CrewSummary crewSummary : crewSummaries) {
            final String nickname = crewSummary.nickname();
            final int absenceCount = crewSummary.absenceCount();
            final int tardinessCount = crewSummary.tardinessCount();
            final Punishment punishment = crewSummary.punishment();
            final String punishmentDisplayName = punishment.getPunishmentName();

            if (punishment.equals(Punishment.NONE)) {
                continue;
            }
            final String outputFormat = "- %s: 결석 %d회, 지각 %d회 (%s)";
            printMessage(String.format(outputFormat, nickname, absenceCount, tardinessCount, punishmentDisplayName));
        }
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }

    private static void printMessageWithLineSeparator(String message) {
        System.out.println("\n" + message + "\n");
    }
}
