package domain;

import except.AttendanceException;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceTime {

    private static final String SCHOOL_DOENST_OPEN = "학교 운영 시간은 8시부터 23시까지 입니다.";
    private static final int MONDAY_SCHOOL_START_HOUR = 13;
    private static final int MONDAY_SCHOOL_START_MINUTE = 0;
    private static final int NORMAL_SCHOOL_START_HOUR = 10;
    private static final int NORMAL_SCHOOL_START_MINI = 0;
    private static final LocalTime SCHOOL_START_HOUR = LocalTime.of(8, 0);
    private static final LocalTime SCHOOL_CLOSE_TIME = LocalTime.of(23, 0);

    private final LocalTime attendanceTime;

    private final LocalTime schoolAttendanceStartTime;

    public AttendanceTime(LocalTime attendanceTime, AttendanceDate attendanceDate) {
        validateAttendanceTime(attendanceTime);
        this.attendanceTime = attendanceTime;
        boolean isMonday = attendanceDate.isMonday();
        if (isMonday) {
            schoolAttendanceStartTime = LocalTime.of(MONDAY_SCHOOL_START_HOUR, MONDAY_SCHOOL_START_MINUTE);
            return;
        }
        schoolAttendanceStartTime = LocalTime.of(NORMAL_SCHOOL_START_HOUR, NORMAL_SCHOOL_START_MINI);
    }

    private void validateAttendanceTime(LocalTime attendanceTime) {
        if (attendanceTime.isBefore(SCHOOL_START_HOUR)) {
            throw new AttendanceException(SCHOOL_DOENST_OPEN);
        }
        if (attendanceTime.isAfter(SCHOOL_CLOSE_TIME)) {
            throw new AttendanceException(SCHOOL_DOENST_OPEN);
        }
    }

    public int minuteFromSchoolStartTime() {
        var attendanceMinute = attendanceTime.toSecondOfDay() / 60;
        var schoolStartTimeMinute = schoolAttendanceStartTime.toSecondOfDay() / 60;
        return attendanceMinute - schoolStartTimeMinute;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceTime that = (AttendanceTime) o;
        return Objects.equals(attendanceTime, that.attendanceTime) && Objects.equals(
                schoolAttendanceStartTime, that.schoolAttendanceStartTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceTime, schoolAttendanceStartTime);
    }
}
