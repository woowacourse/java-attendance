package domain;

public class CrewAttendance {
    private final AttendanceTime attendanceTime;
    private final CrewAttendanceStatus crewAttendanceStatus;

    public CrewAttendance(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendanceTime = attendanceTime;
        crewAttendanceStatus = new CrewAttendanceStatus(attendanceDate, attendanceTime);
    }

    public AttendanceStatus attendanceStatus() {
        return crewAttendanceStatus.attendanceStatus();
    }
}

