package view.dto;

import domain.Attendance;
import domain.AttendanceStatus;
import java.time.LocalDateTime;

public record ChangeAttendanceLogDto(LocalDateTime originalTime, LocalDateTime changeTime,
                                     AttendanceStatus originalStatus, AttendanceStatus changeStatus) {
    public static ChangeAttendanceLogDto from(Attendance originalAttendance, Attendance changeAttendance) {
        return new ChangeAttendanceLogDto(originalAttendance.getDate(), changeAttendance.getDate(),
                originalAttendance.calculateAttendanceStatus(), changeAttendance.calculateAttendanceStatus());
    }
}
