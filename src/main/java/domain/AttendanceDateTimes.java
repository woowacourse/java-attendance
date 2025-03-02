package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDateTimes {
    private final List<AttendanceDateTime> attendanceDateTimes;

    public AttendanceDateTimes(List<LocalDateTime> attendanceDateTimes) {
        this.attendanceDateTimes = new ArrayList<>(attendanceDateTimes.stream()
                .map(AttendanceDateTime::new)
                .toList());
    }

    public AttendanceDateTimes add(LocalDateTime attendanceDateTime) {
        this.attendanceDateTimes.add(new AttendanceDateTime(attendanceDateTime));
        return this;
    }

    public boolean contains(LocalDate attendanceDate) {
        return attendanceDateTimes.stream()
                .map(AttendanceDateTime::toDate)
                .anyMatch(attendanceDate::isEqual);
    }

    public AttendanceDateTime remove(LocalDate targetDate) {
        return attendanceDateTimes.stream()
                .filter(dateTime -> dateTime.toDate().isEqual(targetDate))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 기록이 없는 날짜는 수정할 수 없습니다."));
    }
}
