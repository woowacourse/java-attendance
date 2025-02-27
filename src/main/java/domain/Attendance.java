package domain;

public class Attendance {

    private final Crew crew;
    private final AttendanceTime attendanceTime;

    public Attendance(Crew crew, AttendanceTime attendanceTime) {
        this.crew = crew;
        this.attendanceTime = attendanceTime;
    }
}
