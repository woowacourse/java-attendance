package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);

    private final LocalDate date;
    private final LocalTime time;
    private final AttendanceStatus status;

    public Attendance(LocalDateTime dateTime) {
        validate(dateTime);

        this.date = dateTime.toLocalDate();
        this.time = dateTime.toLocalTime();
        this.status = AttendanceStatus.calculateStatus(dateTime);
    }

    public boolean has(LocalDate day) {
        return this.date.isEqual(day);
    }

    private void validate(LocalDateTime dateTime) {
        if (Holiday.isHoliday(dateTime.toLocalDate()) ||
                dateTime.getDayOfWeek().compareTo(DayOfWeek.FRIDAY) >= 1) {
            throw new IllegalArgumentException("주말 또는 공휴일에는 출석할 수 없습니다");
        }
        if (AttendanceTime.isOverOperatingTime(dateTime)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간에만 출석할 수 있습니다");
        }
    }
}
