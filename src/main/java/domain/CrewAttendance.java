package domain;

import java.time.LocalTime;
import java.util.Objects;

public class CrewAttendance {

    private final AttendanceTime attendanceTime;
    private final AttendanceStatus attendanceStatus;
    private static final int LATE_STANDARD_MIN = 5;
    private static final int ABSENCE_STANDARD_MIN = 30;

    public CrewAttendance(AttendanceTime attendanceTime) {
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = calculateAttendanceStatus(attendanceTime);
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

    public LocalTime attendanceTime() {
        return attendanceTime.getAttendanceTime();
    }

    public String attendanceStatusMessage() {
        return attendanceStatus().getStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrewAttendance that = (CrewAttendance) o;
        return Objects.equals(attendanceTime, that.attendanceTime) && Objects.equals(
                attendanceStatus, that.attendanceStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceTime, attendanceStatus);
    }
}

