package attendance.domain;

import java.time.LocalDateTime;

public class Attendance {
    private final String crewName;
    private LocalDateTime attendanceTime;

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


    public boolean isSameByNameAndDay(String name, int day) {
        return crewName.equals(name) && day == attendanceTime.getDayOfMonth();
    }

    public void modifyAttendanceTime(LocalDateTime modifyTime) {
        this.attendanceTime = modifyTime;
    }
}
