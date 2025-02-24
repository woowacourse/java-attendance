package view;

import domain.Attendance;
import domain.AttendanceCounter;
import domain.AttendanceDateTime;
import domain.AttendanceStatus;
import domain.Attendances;
import domain.Crew;
import domain.Crews;
import domain.Nickname;
import domain.Punishment;
import java.time.LocalDateTime;
import java.time.LocalTime;
import util.Constants;

public final class OutputView {

    private OutputView() {
    }

    public static void printAttendance(final Attendance attendance) {
        final AttendanceDateTime attendanceDateTime = attendance.getAttendanceDateTime();
        final AttendanceStatus attendanceStatus = attendance.getAttendanceStatus();
        final LocalTime localTime = attendanceDateTime.getTime();
        final String formattedTime = getFormattedTime(localTime);

        printFormattedAttendance(attendanceDateTime, formattedTime, attendanceStatus);
    }

    public static void printUpdateAttendance(final Attendance oldAttendance, final Attendance newAttendance) {
        final LocalDateTime oldLocalDateTime = oldAttendance.getLocalDateTime();
        final AttendanceStatus oldAttendanceStatus = oldAttendance.getAttendanceStatus();
        final LocalDateTime newLocalDateTime = newAttendance.getLocalDateTime();
        final AttendanceStatus newAttendanceStatus = newAttendance.getAttendanceStatus();
        final LocalTime newLocalTime = AttendanceDateTime.getTime(newLocalDateTime);

        System.out.println(
                String.format("%s (%s) -> %s (%s) 수정 완료!",
                        oldLocalDateTime.format(AttendanceDateTime.KOREAN_DATE_TIME_FORMAT),
                        oldAttendanceStatus.getDisplayName(), newLocalTime, newAttendanceStatus.getDisplayName()));
    }

    public static void printCrewAttendances(final Crew crew) {
        final Nickname nickname = crew.getNickname();
        final Attendances attendances = crew.getAttendances();
        final AttendanceCounter attendanceCounter = AttendanceCounter.of(attendances);
        final int sum = sumPunishmentCount(attendanceCounter);
        final Punishment punishment = Punishment.findByAbsenceCount(sum);

        printCrewAttedancesFormat(nickname, attendances, attendanceCounter, punishment);
    }

    private static void printCrewAttedancesFormat(Nickname nickname, Attendances attendances,
                                                  AttendanceCounter attendanceCounter,
                                                  Punishment punishment) {
        System.out.println(String.format("이번 달 %s의 출석 기록입니다.", nickname.getNickname()));
        System.out.println();

        printAttendances(attendances);
        System.out.println();

        printAttendanceCounterResult(attendanceCounter);
        System.out.println(String.format("%s 대상자입니다.", punishment.getPunishmentName()));
    }

    private static void printAttendances(Attendances attendances) {
        for (Attendance attendance : attendances.getAttendances()) {
            printAttendanceWithAbsence(attendance);
        }
    }

    private static void printAttendanceWithAbsence(Attendance attendance) {
        final AttendanceDateTime attendanceDateTime = attendance.getAttendanceDateTime();
        final AttendanceStatus attendanceStatus = attendance.getAttendanceStatus();
        final LocalTime localTime = attendanceDateTime.getTime();
        final String formattedTime = getFormattedTime(localTime);
        printFormattedAttendance(attendanceDateTime, formattedTime, attendanceStatus);
    }

    private static void printFormattedAttendance(AttendanceDateTime attendanceDateTime, String formattedTime,
                                                 AttendanceStatus attendanceStatus) {
        final LocalDateTime localDateTime = attendanceDateTime.getDateTime();
        System.out.println(
                String.format("%s %s (%s)",
                        localDateTime.format(AttendanceDateTime.KOREAN_DATE_TIME_FORMAT),
                        formattedTime,
                        attendanceStatus.getDisplayName()));
    }

    private static String getFormattedTime(LocalTime localTime) {
        if (localTime.equals(Constants.ABSENCE_TIME)) {
            return "--:--";
        }
        return localTime.toString();
    }

    public static void printAllExpulsion(final Crews crews) {
        System.out.println("제적 위험자 조회");
        for (Crew crew : crews.getSortedCrews()) {
            printExpulsionByCrew(crew);
        }
    }

    private static void printExpulsionByCrew(Crew crew) {
        final String nickname = crew.getNickname().getNickname();
        final Attendances attendances = crew.getAttendances();
        final AttendanceCounter attendanceCounter = AttendanceCounter.of(attendances);
        final int sumPunishmentCount = sumPunishmentCount(attendanceCounter);
        final Punishment punishment = Punishment.findByAbsenceCount(sumPunishmentCount);
        if (punishment.equals(Punishment.NONE)) {
            return;
        }
        printPunishmentResult(nickname, attendanceCounter, punishment);
    }

    private static void printPunishmentResult(String nickname, AttendanceCounter attendanceCounter,
                                              Punishment punishment) {
        System.out.println(String.format("- %s: 결석 %d회, 지각 %d회 (%s)",
                nickname,
                attendanceCounter.getAbsenceCount(),
                attendanceCounter.getTardinessCount(),
                punishment.getPunishmentName()));
    }

    private static int sumPunishmentCount(AttendanceCounter attendanceCounter) {
        final int absence = attendanceCounter.getAbsenceCount();
        final int tardiness = attendanceCounter.getTardinessCount();
        return (tardiness * 3) + absence;
    }

    private static void printAttendanceCounterResult(AttendanceCounter attendanceCounter) {
        System.out.println(String.format("%s: %d회", AttendanceStatus.ATTENDANCE.getDisplayName(),
                attendanceCounter.getAttendanceCount()));
        System.out.println(String.format("%s: %d회", AttendanceStatus.TARDINESS.getDisplayName(),
                attendanceCounter.getTardinessCount()));
        System.out.println(String.format("%s: %d회", AttendanceStatus.ABSENCE.getDisplayName(),
                attendanceCounter.getAbsenceCount()));
        System.out.println();
    }
}
