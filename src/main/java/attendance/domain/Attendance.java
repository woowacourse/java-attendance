package attendance.domain;

public class Attendance {

    private final AttendanceDate attendanceDate;
    private final AttendanceTime attendanceTime;

    public Attendance(
        final AttendanceDate attendanceDate,
        final AttendanceTime attendanceTime
    ) {
        validateNotNull(attendanceDate, attendanceTime);
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    private void validateNotNull(
        final AttendanceDate attendanceDate,
        final AttendanceTime attendanceTime
    ) {
        if (attendanceDate == null || attendanceTime == null) {
            throw new IllegalArgumentException("출석은 출석 날짜와 출석 시간을 가지고 있어야 합니다.");
        }
    }
}
