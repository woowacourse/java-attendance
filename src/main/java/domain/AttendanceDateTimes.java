package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceDateTimes {
    private final List<LocalDateTime> attendanceDateTimes;

    public AttendanceDateTimes(List<LocalDateTime> attendanceDateTimes) {
        this.attendanceDateTimes = attendanceDateTimes;
    }

    public AttendanceDateTimes add(LocalDateTime attendanceDateTime) {
        this.attendanceDateTimes.add(attendanceDateTime);
        return this;
    }

    public boolean contains(LocalDate attendanceDate) {
        return attendanceDateTimes.stream()
                .map(LocalDateTime::toLocalDate)
                .anyMatch(attendanceDate::isEqual);
    }

    public LocalDateTime remove(LocalDate targetDate) {
        return attendanceDateTimes.stream()
                .filter(dateTime -> dateTime.toLocalDate().isEqual(targetDate))
                .findAny()
                .orElse(null);
    }
}
