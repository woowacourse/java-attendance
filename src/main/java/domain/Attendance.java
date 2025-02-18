package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Attendance {
    public static final LocalTime MAX_ATTENDANCE_TIME = LocalTime.of(23, 0);
    public static final LocalTime MIN_ATTENDANCE_TIME = LocalTime.of(8, 0);
    public static final List<DayOfWeek> WEEKEND = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    private String name;
    private LocalDateTime attendanceTime;

    public Attendance(String name, LocalDateTime attendanceTime) {
        validateDate(attendanceTime.toLocalDate());
        validateTime(attendanceTime.toLocalTime());
        this.name = name;
        this.attendanceTime = attendanceTime;
    }

    private void validateDate(LocalDate date) {
        // TODO : 주말, 공휴일이면 '출석 가능한 날짜가 아닙니다.'
        if (WEEKEND.contains(date.getDayOfWeek())) {
            throw new IllegalArgumentException("출석 가능한 날짜가 아닙니다.");
        }
    }

    private void validateTime(LocalTime time) {
        if (time.isAfter(MAX_ATTENDANCE_TIME) || time.isBefore(MIN_ATTENDANCE_TIME)) {
            throw new IllegalArgumentException("출석 가능한 시간이 아닙니다.");
        }
    }
}
