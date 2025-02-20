package attendance.domain;

public class Crew {
    private final String name;
    private final AttendanceHistoryManager attendanceHistoryManager;

    public Crew(String name) {
        this.name =  name;
        this.attendanceHistoryManager = new AttendanceHistoryManager();
    }

    public void addAttendanceResult(AttendanceHistory attendanceHistory) {
        attendanceHistoryManager.addAttendanceResult(attendanceHistory);
    }

    public void modifyAttendanceResult(AttendanceHistory attendanceHistory) {
        attendanceHistoryManager.modifyAttendanceResult(attendanceHistory);
    }


    public String getName() {
        return name;
    }

    public AttendanceHistoryManager getAttendanceHistory() {
         return attendanceHistoryManager;
    }

    public AttendanceHistory getAttendanceResult(AttendanceHistory attendanceHistory) {
        return attendanceHistoryManager.getAttendanceHistory(attendanceHistory);
    }
}
