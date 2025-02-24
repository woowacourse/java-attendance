package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AttendanceTimes {
    private final List<AttendanceTime> attendanceLog;

    private AttendanceTimes(List<AttendanceTime> attendanceLog) {
        this.attendanceLog = attendanceLog;
    }

    public static AttendanceTimes of(List<AttendanceTime> attendanceTimes) {
        return new AttendanceTimes(attendanceTimes);
    }

    public boolean contains(LocalDate date) {
        return attendanceLog.stream()
                .anyMatch(attendanceTime -> attendanceTime.isSameDate(date));
    }

    public LocalDateTime readAttendance(LocalDate date) {
        return attendanceLog.stream()
                .filter(attendanceTime -> attendanceTime.isSameDate(date))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 출석기록이 존재하지 않습니다."))
                .toLocalDateTime();
    }

    public boolean modifyAttendance(LocalDate date, LocalTime time) {
        AttendanceTime toModify = attendanceLog.stream()
                .filter(attendanceTime -> attendanceTime.isSameDate(date))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 출석기록이 존재하지 않습니다."));
        toModify.modify(time);
        return true;
    }
}
