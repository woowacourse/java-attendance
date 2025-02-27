package domain;

import java.time.LocalTime;
import java.util.Objects;

public class CrewAttendance {
    
    private final AttendanceTime attendanceTime;
    private final CrewAttendanceStatus crewAttendanceStatus;

    public CrewAttendance(AttendanceTime attendanceTime) {
        this.attendanceTime = attendanceTime;
        crewAttendanceStatus = new CrewAttendanceStatus(attendanceTime);
    }

    public AttendanceStatus attendanceStatus() {
        return crewAttendanceStatus.attendanceStatus();
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
                crewAttendanceStatus, that.crewAttendanceStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceTime, crewAttendanceStatus);
    }
}

