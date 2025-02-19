package attendance.domain;

import java.time.LocalDateTime;

public class Attendance {
    private final String crewName;
    private final LocalDateTime attendanceTime;

    public Attendance(String crewName, LocalDateTime attendanceTime) {
        this.crewName = crewName;
        this.attendanceTime = attendanceTime;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public String getCrewName() {
        return crewName;
    }

    public boolean isAlreadyAttendance(Attendance currentAttendance) {

        if (!crewName.equals(currentAttendance.crewName)) {
            return false;
        }

        return attendanceTime.toLocalDate().isEqual(currentAttendance.attendanceTime.toLocalDate());
    }
}
