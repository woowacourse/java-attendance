package attendance.domain;

import java.time.LocalTime;
import java.util.function.BiPredicate;

public enum AttendanceStatus {

    ATTENDANCE_COMPLETE("출석", (attendance, attendanceTime) -> {
        LocalTime startInclusive = CampusTime.CAMPUS_OPEN_TIME.getLocalTime();
        if (attendance.isMonday()) {
            return attendanceTime.isBetweenInclusive(startInclusive,
                    CampusTime.MONDAY_LECTURE_START_TIME.getLocalTime().plusMinutes(5L));
        }
        return attendanceTime.isBetweenInclusive(startInclusive,
                CampusTime.TUESDAY_TO_FRIDAY_LECTURE_START_TIME.getLocalTime().plusMinutes(5L));
    }),
    LATE("지각", (attendance, attendanceTime) -> {
        if (attendance.isMonday()) {
            return attendanceTime.isBetweenInclusive(
                    CampusTime.MONDAY_LECTURE_START_TIME.getLocalTime().plusMinutes(6L),
                    CampusTime.MONDAY_LECTURE_START_TIME.getLocalTime().plusMinutes(30L));
        }
        return attendanceTime.isBetweenInclusive(
                CampusTime.TUESDAY_TO_FRIDAY_LECTURE_START_TIME.getLocalTime().plusMinutes(6L),
                CampusTime.TUESDAY_TO_FRIDAY_LECTURE_START_TIME.getLocalTime().plusMinutes(30L)
        );
    }),
    ABSENT("결석", (attendance, attendanceTime) -> {
        if (attendance.isMonday()) {
            return attendanceTime.isBetweenInclusive(
                    CampusTime.MONDAY_LECTURE_START_TIME.getLocalTime().plusMinutes(31L),
                    CampusTime.CAMPUS_CLOSE_TIME.getLocalTime());
        }
        return attendanceTime.isBetweenInclusive(
                CampusTime.TUESDAY_TO_FRIDAY_LECTURE_START_TIME.getLocalTime().plusMinutes(31L),
                CampusTime.CAMPUS_CLOSE_TIME.getLocalTime()
        );
    });

    private static final int LATE_PER_ABSENT = 3;

    private final String text;
    private final BiPredicate<Attendance, AttendanceTime> condition;

    AttendanceStatus(final String text, final BiPredicate<Attendance, AttendanceTime> condition) {
        this.text = text;
        this.condition = condition;
    }

    public static boolean isAttendance(final Attendance attendance, final AttendanceTime attendanceTime) {
        return ATTENDANCE_COMPLETE.condition.test(attendance, attendanceTime);
    }

    public static boolean isLate(final Attendance attendance, final AttendanceTime attendanceTime) {
        return LATE.condition.test(attendance, attendanceTime);
    }

    public static boolean isAbsent(final Attendance attendance, final AttendanceTime attendanceTime) {
        return ABSENT.condition.test(attendance, attendanceTime);
    }

    public static int calculateTotalAbsentCount(final int absentCount, final int lateCount) {
        return absentCount + lateCount / LATE_PER_ABSENT;
    }

    public static int convertToLateCount(final int absentCount) {
        return absentCount * LATE_PER_ABSENT;
    }

    public String getText() {
        return this.text;
    }
}
