package domain;

public class Attendance {

    private final Crew crew;
    private final AttendanceTime attendanceTime;

    public Attendance(Crew crew, AttendanceTime attendanceTime) {
        this.crew = crew;
        this.attendanceTime = attendanceTime;
    }

    public boolean isSameCrewAndTime(Attendance otherAttendance) {
        return crew.equals(otherAttendance.crew) && this.attendanceTime.isSameDay(otherAttendance.attendanceTime);
    }
}
