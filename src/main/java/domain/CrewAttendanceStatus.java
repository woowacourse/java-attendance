package domain;


import java.util.Objects;

public class CrewAttendanceStatus {

    private static final int LATE_STANDARD_MIN = 5;
    private static final int ABSENCE_STANDARD_MIN = 30;

    private final AttendanceStatus attendanceStatus;

    public CrewAttendanceStatus(AttendanceTime attendanceTime) {
        attendanceStatus = calculateAttendanceStatus(attendanceTime);
    }

    private AttendanceStatus calculateAttendanceStatus(AttendanceTime attendanceTime) {
        int min = attendanceTime.minuteFromSchoolStartTime();
        if (min > ABSENCE_STANDARD_MIN) {
            return AttendanceStatus.ABSENCE;
        }
        if (min > LATE_STANDARD_MIN) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    public AttendanceStatus attendanceStatus() {
        return attendanceStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrewAttendanceStatus that = (CrewAttendanceStatus) o;
        return attendanceStatus == that.attendanceStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceStatus);
    }
}
