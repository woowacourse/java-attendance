package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import attendance.dto.AttendanceDateDto;
import attendance.dto.AttendanceTimeDto;

public class Attendances {
    private final Map<LocalDate, Attendance> attendances = new HashMap<>();

    public void addAttendance(LocalDateTime time, AttendanceStatus attendanceStatus) {
        Attendance attendance = new Attendance(time.toLocalTime(), attendanceStatus);
        attendances.put(time.toLocalDate(), attendance);
    }

    public AttendanceDateDto getAttendanceTime(LocalDate time) {
        Attendance attendance = attendances.get(time);
        AttendanceTimeDto attendanceTimeDto = attendance.getAttendanceResult();
        return AttendanceDateDto.generateAttendanceDateDto(time, attendanceTimeDto);
    }
}
