package view.dto;

import domain.Attendance;
import domain.AttendanceStatus;
import java.time.LocalDateTime;

public record AttendanceLogDTO(LocalDateTime localDateTime, AttendanceStatus attendanceStatus) implements
        Comparable<AttendanceLogDTO> {
    public static AttendanceLogDTO from(Attendance attendance) {
        return new AttendanceLogDTO(attendance.getDate(), attendance.calculateAttendanceStatus());
    }

    @Override
    public int compareTo(AttendanceLogDTO o) {
        return this.localDateTime.compareTo(o.localDateTime);
    }
}
