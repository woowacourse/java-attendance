package attendance.dto;

import attendance.model.AttendanceBook;
import attendance.model.AttendanceLog;
import attendance.model.AttendanceType;
import attendance.model.Nickname;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AttendanceLogDto(
        Nickname nickname,
        LocalDate attendanceDate,
        LocalTime attendanceTime,
        AttendanceType attendanceType
) {
    public static List<AttendanceLogDto> mapToDtos(AttendanceBook attendanceBook, List<AttendanceLog> attendanceLogs) {
        return attendanceLogs.stream()
                .map(attendanceLog -> {
                    AttendanceType attendanceType = attendanceBook.determineAttendanceType(
                            attendanceLog.getAttendanceDate(),
                            attendanceLog.getAttendanceTime());
                    return AttendanceLogDto.from(attendanceLog, attendanceType);
                })
                .toList();
    }

    private static AttendanceLogDto from(AttendanceLog attendanceLog, AttendanceType attendanceType) {
        return new AttendanceLogDto(
                attendanceLog.getNickname(),
                attendanceLog.getAttendanceDate(),
                attendanceLog.getAttendanceTime(),
                attendanceType);
    }
}
