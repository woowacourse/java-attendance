package domain;

public record Attendance(
        AttendanceDate attendanceDate,
        AttendanceTime attendanceTime,
        AttendanceStatus status
) {
    public static Attendance of(final AttendanceDate attendanceDate, final AttendanceTime attendanceTime) {
        final AttendanceStatus status = AttendanceStatus.findByTime(attendanceDate.getDayOfWeek(),
                attendanceTime.getLocaltime());
        return new Attendance(attendanceDate, attendanceTime, status);
    }
}
