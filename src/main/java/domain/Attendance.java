package domain;

import static domain.AttendanceCode.ABSENT;
import static domain.AttendanceCode.LATE;
import static domain.AttendanceCode.PRESENT;
import static domain.DayOfWeek.MONDAY;
import static domain.DayOfWeek.TUESDAY;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import util.DayConverter;

public class Attendance {
    private final LocalDateTime attendanceTime;

    public Attendance(LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public AttendanceCode calculateAttendanceCode() {
        LocalTime time = attendanceTime.toLocalTime();
        Duration duration = Duration.between(TUESDAY.getOpenTime(), time);
        if (attendanceTime.getDayOfWeek().getValue() == MONDAY.getDayOfWeekCode()) {
            duration = Duration.between(MONDAY.getOpenTime(), time);
        }

        if (duration.toMinutes() <= PRESENT.getUpperBound()) {
            return PRESENT;
        }
        if (duration.toMinutes() >= LATE.getLowerBound() && duration.toMinutes() <= LATE.getUpperBound()) {
            return LATE;
        }
        return ABSENT;
    }


    public void validateHoliday(List<Integer> holidays) {
        DayOfMonth dayOfMonth = new DayOfMonth(attendanceTime.getDayOfMonth());
        if (dayOfMonth.isHoliday(holidays, attendanceTime.toLocalDate())) {
            throw new IllegalArgumentException(
                    String.format("%d월 %d일 %s은 등교일이 아닙니다.",
                            attendanceTime.getMonth().getValue(),
                            attendanceTime.getDayOfMonth(),
                            DayConverter.getKoreanDayOfWeek(attendanceTime.toLocalDate())));
        }
    }
}
