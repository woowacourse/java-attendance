package view;

import domain.Attendance;
import domain.AttendanceCounter;
import domain.AttendanceDateTime;
import domain.AttendanceStatus;
import domain.Attendances;
import domain.Crew;
import domain.Crews;
import util.DayOfWeekKorean;
import domain.Nickname;
import domain.Punishment;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import util.Constants;

public final class OutputView {

    private OutputView() {
    }

    public static void printAttendance(final Attendance attendance) {
        final LocalDateTime localDateTime = attendance.getLocalDateTime();
        final AttendanceStatus attendanceStatus = attendance.getAttendanceStatus();
        final int day = localDateTime.getDayOfMonth();
        final String dayName = DayOfWeekKorean.getKoreanName(localDateTime.getDayOfWeek());
        final LocalTime localTime = AttendanceDateTime.getLocalTimeByLocalDateTime(localDateTime);

        System.out.println(
                String.format("%d월 %02d일 %s %s (%s)", Constants.FIXED_MONTH, day, dayName, localTime,
                        attendanceStatus.getDisplayName()));
    }

    public static void printUpdateAttendance(final Attendance oldAttendance, final Attendance newAttendance) {
        final LocalDateTime oldLocalDateTime = oldAttendance.getLocalDateTime();
        final AttendanceStatus oldAttendanceStatus = oldAttendance.getAttendanceStatus();
        final int oldDay = oldLocalDateTime.getDayOfMonth();
        final String oldDayName = DayOfWeekKorean.getKoreanName(oldLocalDateTime.getDayOfWeek());
        final LocalTime oldLocalTime = AttendanceDateTime.getLocalTimeByLocalDateTime(oldLocalDateTime);

        final LocalDateTime newLocalDateTime = newAttendance.getLocalDateTime();
        final AttendanceStatus newAttendanceStatus = newAttendance.getAttendanceStatus();
        final LocalTime newLocalTime = AttendanceDateTime.getLocalTimeByLocalDateTime(newLocalDateTime);

        System.out.println(
                String.format("%d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!", Constants.FIXED_MONTH, oldDay, oldDayName,
                        oldLocalTime,
                        oldAttendanceStatus.getDisplayName(), newLocalTime, newAttendanceStatus.getDisplayName()));
    }

    public static void printCrewAttendances(final Crew crew) {
        final Nickname nickname = crew.getNickname();
        final Attendances attendances = crew.getAttendances();

        final AttendanceCounter attendanceCounter = AttendanceCounter.of(attendances);
        final int attendanceCount = attendanceCounter.getAttendanceCount();
        final int tardiness = attendanceCounter.getTardiness();
        final int absence = attendanceCounter.getAbsence();

        int sum = (tardiness * 3) + absence;
        final Punishment punishment = Punishment.findByAbsenceCount(sum);

        System.out.println(String.format("이번 달 %s의 출석 기록입니다.", nickname.getNickname()));
        System.out.println();

        for (Attendance attendance : attendances.getAttendances()) {
            final AttendanceStatus attendanceStatus = attendance.getAttendanceStatus();
            final LocalDateTime localDateTime = attendance.getLocalDateTime();
            final int day = localDateTime.getDayOfMonth();
            final DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
            final LocalTime localTime = AttendanceDateTime.getLocalTimeByLocalDateTime(localDateTime);
            String timeFormat = String.valueOf(localTime);
            if (localTime.equals(Constants.ABSENCE_TIME)) {
                timeFormat = "--:--";
            }
            System.out.println(
                    String.format("%d월 %02d일 %s %s (%s)", Constants.FIXED_MONTH, day, DayOfWeekKorean.getKoreanName(dayOfWeek),
                            timeFormat,
                            attendanceStatus.getDisplayName()));
        }
        System.out.println();

        System.out.println(String.format("%s: %d회", AttendanceStatus.ATTENDANCE.getDisplayName(), attendanceCount));
        System.out.println(String.format("%s: %d회", AttendanceStatus.TARDINESS.getDisplayName(), tardiness));
        System.out.println(String.format("%s: %d회", AttendanceStatus.ABSENCE.getDisplayName(), absence));
        System.out.println();

        System.out.println(String.format("%s 대상자입니다.", punishment.getPunishmentName()));
    }

    public static void printAllExpulsion(final Crews crews) {
        System.out.println("제적 위험자 조회");
        for (Crew crew : crews.getSortedCrews()) {
            final String nickname = crew.getNickname().getNickname();
            final Attendances attendances = crew.getAttendances();
            final AttendanceCounter attendanceCounter = AttendanceCounter.of(attendances);
            final int absence = attendanceCounter.getAbsence();
            final int tardiness = attendanceCounter.getTardiness();
            final int sum = (tardiness * 3) + absence;
            final Punishment punishment = Punishment.findByAbsenceCount(sum);

            if (punishment.equals(Punishment.NONE)) {
                continue;
            }
            System.out.println(String.format("- %s: 결석 %d회, 지각 %d회 (%s)", nickname, absence, tardiness,
                    punishment.getPunishmentName()));
        }
    }
}
