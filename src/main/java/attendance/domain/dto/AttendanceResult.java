package attendance.domain.dto;

import attendance.domain.Attendance;

public class AttendanceResult {
    private final int attendanceMonth;
    private final int attendanceDay;
    private final int attendanceHour;
    private final int attendanceMinute;
    private final String attendanceStatus;

    public AttendanceResult(Attendance attendance) {
        this.attendanceMonth = attendance.getAttendanceDate().getMonthValue();
        this.attendanceDay = attendance.getAttendanceDate().getDayOfMonth();
        this.attendanceHour = attendance.getAttendanceTime().getHour();
        this.attendanceMinute = attendance.getAttendanceTime().getMinute();
        this.attendanceStatus = attendance.getAttendanceStatus();
    }

    public int getAttendanceMonth() {
        return attendanceMonth;
    }

    public int getAttendanceDay() {
        return attendanceDay;
    }

    public int getAttendanceHour() {
        return attendanceHour;
    }

    public int getAttendanceMinute() {
        return attendanceMinute;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }
}
