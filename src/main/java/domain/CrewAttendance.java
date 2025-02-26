package domain;

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
}

