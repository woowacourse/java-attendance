package domain;


public class CrewAttendanceStatus {

    private static final int LATE_STANDARD_MIN = 5;
    private static final int ABSENCE_STANDARD_MIN = 30;

    private final AttendanceStatus attendanceStatus;

    public CrewAttendanceStatus(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        attendanceStatus = calculateAttendanceStatus(attendanceDate, attendanceTime);
    }

    private AttendanceStatus calculateAttendanceStatus(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        int min = attendanceTime.minuteFromSchoolStartTime(attendanceDate.isMonday());
        if (min > ABSENCE_STANDARD_MIN) {
            return AttendanceStatus.ABSENCE;
        }
        if (min > LATE_STANDARD_MIN) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    public AttendanceStatus attendanceStatus() {
        return attendanceStatus;
    }
}
