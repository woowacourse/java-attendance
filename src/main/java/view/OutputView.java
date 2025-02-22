package view;

import domain.Attendance;
import domain.AttendanceCounter;
import domain.AttendanceStatus;
import domain.Attendances;
import domain.Crew;
import domain.CrewSummary;
import domain.Nickname;
import domain.Punishment;
import domain.Week;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import util.Constants;

public final class OutputView {

    private OutputView() {
    }

    public static void printAttendance(Attendance attendance) {
        final LocalDateTime localDateTime = attendance.getLocalDateTime();
        final AttendanceStatus attendanceStatus = attendance.getAttendanceStatus();
        final int day = localDateTime.getDayOfMonth();
        final String dayName = Week.findKoreanName(localDateTime.getDayOfWeek());
        final LocalTime localTime = localDateTime.toLocalTime();

        System.out.println(
                String.format("%d월 %02d일 %s %s (%s)", Constants.FIXED_MONTH, day, dayName, localTime,
                        attendanceStatus.getKoreanName()));
    }

    public static void printUpdateAttendance(final Attendance oldAttendance, final Attendance newAttendance) {
        final LocalDateTime oldLocalDateTime = oldAttendance.getLocalDateTime();
        final AttendanceStatus oldAttendanceStatus = oldAttendance.getAttendanceStatus();
        final int oldDay = oldLocalDateTime.getDayOfMonth();
        final String oldDayName = Week.findKoreanName(oldLocalDateTime.getDayOfWeek());
        final LocalTime oldLocalTime = oldLocalDateTime.toLocalTime();

        final LocalDateTime newLocalDateTime = newAttendance.getLocalDateTime();
        final AttendanceStatus newAttendanceStatus = newAttendance.getAttendanceStatus();
        final LocalTime newLocalTime = newLocalDateTime.toLocalTime();

        System.out.println(
                String.format("%d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!", Constants.FIXED_MONTH, oldDay, oldDayName,
                        oldLocalTime,
                        oldAttendanceStatus.getKoreanName(), newLocalTime, newAttendanceStatus.getKoreanName()));
    }

    public static void printCrewAttendances(Crew crew) {
        final Nickname nickname = crew.getNickname();
        final Attendances attendances = crew.getAttendances();
        attendances.sort();

        final AttendanceCounter attendanceCounter = AttendanceCounter.of(attendances);
        final int attendanceCount = attendanceCounter.getAttendanceCount();
        final int tardiness = attendanceCounter.getTardiness();
        final int absence = attendanceCounter.getAbsence();

        final Punishment punishment = Punishment.findByAbsenceCount(absence);

        System.out.println(String.format("이번 달 %s의 출석 기록입니다.", nickname.getNickname()));
        System.out.println();

        for (Attendance attendance : attendances.getAttendances()) {
            final AttendanceStatus attendanceStatus = attendance.getAttendanceStatus();
            final LocalDateTime localDateTime = attendance.getLocalDateTime();
            final int day = localDateTime.getDayOfMonth();
            final DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
            final LocalTime localTime = localDateTime.toLocalTime();
            String timeFormat = String.valueOf(localTime);
            if (localTime.equals(LocalTime.of(0, 0))) {
                timeFormat = "--:--";
            }
            System.out.printf("%d월 %02d일 %s %s (%s)",
                    Constants.FIXED_MONTH,
                    day,
                    Week.findKoreanName(dayOfWeek),
                    timeFormat,
                    attendanceStatus.getKoreanName());
        }
        System.out.println();

        System.out.println(String.format("%s: %d회", AttendanceStatus.ATTENDANCE.getKoreanName(), attendanceCount));
        System.out.println(String.format("%s: %d회", AttendanceStatus.TARDINESS.getKoreanName(), tardiness));
        System.out.println(String.format("%s: %d회", AttendanceStatus.ABSENCE.getKoreanName(), absence));
        System.out.println();

        System.out.println(String.format("%s 대상자입니다.", punishment.getPunishmentName()));
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
        System.out.println("\n" + message);
    }
}
