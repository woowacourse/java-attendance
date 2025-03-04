package attendance.domain;

import java.time.LocalTime;

public class AttendanceTime {
    private static final String INVALID_TIME_FORMAT = "[ERROR] 올바르지 않은 출석 시간 형식입니다. 24시간 형식을 준수해주세요.\n";
    private static final String TIME_FORMAT = "^([01][0-9]|2[0-3]):[0-5][0-9]$";

    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);

    private final LocalTime attendanceTime;

    public AttendanceTime(LocalTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public AttendanceTime(String timeInput) {
        validateAttendanceTime(timeInput);
        this.attendanceTime = LocalTime.parse(timeInput);
    }

    private void validateAttendanceTime(final String attendanceTime) {
        if (!attendanceTime.matches(TIME_FORMAT)) {
            throw new IllegalArgumentException(INVALID_TIME_FORMAT);
        }
        LocalTime time = LocalTime.parse(attendanceTime);
        if (time.isBefore(START_TIME) || time.isAfter(END_TIME)) {
            throw new IllegalArgumentException(INVALID_TIME_FORMAT);
        }
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }
}
