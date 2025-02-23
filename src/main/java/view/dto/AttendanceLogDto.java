package view.dto;

import domain.Attendance;
import domain.AttendanceStatus;
import java.time.LocalDateTime;

public record AttendanceLogDto(LocalDateTime localDateTime, AttendanceStatus attendanceStatus) implements
        Comparable<AttendanceLogDto> {
    public static AttendanceLogDto from(Attendance attendance) {
        return new AttendanceLogDto(attendance.getDate(), attendance.calculateAttendanceStatus());
    }

    @Override
    public int compareTo(AttendanceLogDto o) {
        return this.localDateTime.compareTo(o.localDateTime);
    }
}
