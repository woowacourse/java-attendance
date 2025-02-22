package domain;

import java.time.LocalTime;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    TARDINESS("지각"),
    ABSENCE("결석");

    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);

    private final String koreanName;

    AttendanceStatus(String koreanName) {
        this.koreanName = koreanName;
    }

    public static AttendanceStatus findByAttendanceTime(Week day, LocalTime attendanceTime) {
        if (attendanceTime.isBefore(START_TIME) || attendanceTime.isAfter(END_TIME)) {
            return ABSENCE;
        }
        if (attendanceTime.isBefore(day.getAttendanceTime().plusMinutes(5)) || attendanceTime.equals(
                day.getAttendanceTime().plusMinutes(5))) {
            return ATTENDANCE;
        }
        if (attendanceTime.isBefore(day.getAttendanceTime().plusMinutes(30)) || attendanceTime.equals(
                day.getAttendanceTime().plusMinutes(30))) {
            return TARDINESS;
        }
        return ABSENCE;
    }

    public String getKoreanName() {
        return koreanName;
    }
}
