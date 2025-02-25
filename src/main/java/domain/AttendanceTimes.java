package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceTimes {
    private final List<AttendanceTime> attendanceLog;

    private AttendanceTimes(List<AttendanceTime> attendanceLog) {
        this.attendanceLog = new ArrayList<>(attendanceLog);
        ;
    }

    public static AttendanceTimes of(List<AttendanceTime> attendanceTimes) {
        return new AttendanceTimes(attendanceTimes);
    }

    public boolean contains(LocalDate date) {
        return attendanceLog.stream()
                .anyMatch(attendanceTime -> attendanceTime.isSameDate(date));
    }

    public void addAttendance(AttendanceTime time) { // TODO: 파라미터 시그니처 통일하기
        attendanceLog.add(time);
    }

    public LocalDateTime readAttendance(LocalDate date) {
        return attendanceLog.stream()
                .filter(attendanceTime -> attendanceTime.isSameDate(date))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 출석기록이 존재하지 않습니다."))
                .toLocalDateTime();
    }

    public Optional<AttendanceTime> modifyAttendance(LocalDate date, LocalTime time) {
        Optional<AttendanceTime> currentTime = attendanceLog.stream()
                .filter(attendanceTime -> attendanceTime.isSameDate(date))
                .findAny();
        Optional<AttendanceTime> previous = Optional.empty();
        if (currentTime.isEmpty()) {
            attendanceLog.add(AttendanceTime.of(date, time));
            return previous;
        }
        return Optional.of(currentTime.get().modify(time));
    }
}
