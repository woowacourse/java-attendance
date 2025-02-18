package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import attendance.dto.AttendanceDateDto;
import attendance.dto.AttendanceTimeDto;
import attendance.exception.AttendanceException;

public class Attendances {
    private final Map<LocalDate, Attendance> attendances = new HashMap<>();
    private static final String DUPLICATE_ATTENDANCE_DATE = "이미 출석되었습니다. 수정 기능을 이용해주세요.";

    public void addAttendance(LocalDateTime time, AttendanceStatus attendanceStatus) {
        Attendance attendance = new Attendance(time.toLocalTime(), attendanceStatus);
        if (attendances.containsKey(time.toLocalDate())) {
            throw new AttendanceException(DUPLICATE_ATTENDANCE_DATE);
        }
        attendances.put(time.toLocalDate(), attendance);
    }

    public AttendanceDateDto getAttendanceTime(LocalDate time) {
        Attendance attendance = attendances.get(time);
        AttendanceTimeDto attendanceTimeDto = attendance.getAttendanceResult();
        return AttendanceDateDto.generateAttendanceDateDto(time, attendanceTimeDto);
    }
}
