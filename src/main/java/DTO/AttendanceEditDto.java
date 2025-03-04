package DTO;

import domain.attendance.AttendanceDate;

import java.time.LocalDateTime;

public record AttendanceEditDto(
        AttendanceDate beforeAttendance,
        LocalDateTime before,
        AttendanceDate afterAttendance,
        LocalDateTime after) {
    public static AttendanceEditDto from(AttendanceDate before, AttendanceDate after){
        return new AttendanceEditDto(before, before.getAttendanceAt(), after, after.getAttendanceAt());
    }

}
