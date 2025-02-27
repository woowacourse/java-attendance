package attendance.dto;

import attendance.domain.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceEditDto (
    LocalDate editDate, LocalTime beforeEditTime, AttendanceStatus beforeEditStatus,
    LocalTime editTime, AttendanceStatus editStatus
){
    public static AttendanceEditDto of (LocalDate editDate, LocalTime beforeEditTime, AttendanceStatus beforeEditStatus,
                                 LocalTime editTime, AttendanceStatus editStatus) {
        return new AttendanceEditDto(editDate, beforeEditTime, beforeEditStatus, editTime, editStatus);
    }
}
