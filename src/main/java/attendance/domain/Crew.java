package attendance.domain;

public class Crew {
    private final String name;
    private final AttendanceHistory attendanceHistory;

    public Crew(String name) {
        this.name =  name;
        this.attendanceHistory = new AttendanceHistory();
    }

    public void addAttendanceResult(AttendanceResult attendanceResult) {
        attendanceHistory.addAttendanceResult(attendanceResult);
    }

    public void modifyAttendanceResult(AttendanceResult attendanceResult) {
        attendanceHistory.modifyAttendanceResult(attendanceResult);
    }


    public String getName() {
        return name;
    }

    public AttendanceHistory getAttendanceHistory() {
         return attendanceHistory;
    }

    public AttendanceResult getAttendanceResult(AttendanceResult attendanceResult) {
        return attendanceHistory.getAttendanceResult(attendanceResult);
    }
}
