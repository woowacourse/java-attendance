package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import attendance.exception.AttendanceArgumentException;

public class Attendances {
    private final Map<LocalDate, Attendance> attendances = new HashMap<>();
    private static final String DUPLICATE_ATTENDANCE_DATE = "이미 출석되었습니다. 수정 기능을 이용해주세요.";

    public void addAttendance(LocalDateTime time, AttendanceStatus attendanceStatus) {
        Attendance attendance = new Attendance(attendanceStatus,time.toLocalTime());
        if (attendances.containsKey(time.toLocalDate())) {
            throw new AttendanceArgumentException(DUPLICATE_ATTENDANCE_DATE);
        }
        attendances.put(time.toLocalDate(), attendance);
    }

    public LocalTime getAttendanceTime(LocalDate time) {
        return attendances.get(time)
                .time();
    }

    public AttendanceStatus getAttendanceStatus(LocalDate time) {
        return attendances.get(time)
                .attendanceStatus();
    }
}
