package view.dto;

import domain.Attendance;
import domain.AttendanceStatus;
import java.time.LocalDateTime;

public record ChangeAttendanceLogDTO(LocalDateTime originalTime, LocalDateTime changeTime,
                                     AttendanceStatus originalStatus, AttendanceStatus changeStatus) {
    public static ChangeAttendanceLogDTO from(Attendance originalAttendance, Attendance changeAttendance) {
        return new ChangeAttendanceLogDTO(originalAttendance.getDate(), changeAttendance.getDate(),
                originalAttendance.calculateAttendanceStatus(), changeAttendance.calculateAttendanceStatus());
    }
}
