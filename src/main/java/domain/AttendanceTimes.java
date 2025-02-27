package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceTimes {
    private final List<AttendanceTime> attendanceLog;

    private AttendanceTimes(List<AttendanceTime> attendanceLog) {
        this.attendanceLog = new ArrayList<>(attendanceLog);
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
        AttendanceTime dateTime = AttendanceTime.of(date, null); // TODO: null 보다 나은 방법 고민
        return attendanceLog.stream()
                .filter(attendanceTime -> attendanceTime.isSameDate(dateTime))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 출석기록이 존재하지 않습니다."))
                .toLocalDateTime();
    }

    public Optional<AttendanceTime> modifyAttendance(AttendanceTime time) {
        Optional<AttendanceTime> currentTime = attendanceLog.stream()
                .filter(attendanceTime -> attendanceTime.isSameDate(time))
                .findAny();
        Optional<AttendanceTime> previous = Optional.empty();
        if (currentTime.isEmpty()) {
            attendanceLog.add(time);
            return previous;
        }
        return Optional.of(currentTime.get().modify(time));
    }
}
