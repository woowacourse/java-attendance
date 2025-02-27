package attendance.domain;

public class Attendance {

    private final String crewName;
    private final Time attendanceTime;

    public Attendance(String crewName, Time attendanceTime) {
        this.crewName = crewName;
        this.attendanceTime = attendanceTime;
    }

    public boolean isSameLocalDate(Attendance attendance) {
        return attendanceTime.isSameLocalDate(attendance.attendanceTime);
    }
}
