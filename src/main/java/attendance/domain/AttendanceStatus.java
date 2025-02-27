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
    });

    private final String text;
    private final BiPredicate<Attendance, AttendanceTime> condition;

    AttendanceStatus(final String text, final BiPredicate<Attendance, AttendanceTime> condition) {
        this.text = text;
        this.condition = condition;
    }

    public static boolean isAttendance(final Attendance attendance, final AttendanceTime attendanceTime) {
        return ATTENDANCE_COMPLETE.condition.test(attendance, attendanceTime);
    }

}
