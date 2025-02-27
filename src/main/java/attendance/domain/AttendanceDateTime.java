package attendance.domain;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class AttendanceDateTime {

    public static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    public static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);
    private final LocalDateTime dateTime;

    public AttendanceDateTime(LocalDateTime attendanceDateTime) {
        validate(attendanceDateTime);
        this.dateTime = attendanceDateTime;
    }

    private void validate(LocalDateTime attendanceDateTime) {
        validateFuture(attendanceDateTime);
        validateHoliday(attendanceDateTime);
        validateWeekend(attendanceDateTime);
        validateOperationTime(attendanceDateTime);
    }

    private void validateFuture(LocalDateTime attendanceDateTime) {
        if (attendanceDateTime.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("미래의 시간으로는 출석할 수 없습니다.");
        }
    }

    private void validateHoliday(LocalDateTime attendanceDateTime) {
        if (Holiday.isHoliday(attendanceDateTime)) {
            throw new IllegalArgumentException("%d월 %d일 %s은 등교일이 아닙니다.".formatted(
                    attendanceDateTime.getMonthValue(),
                    attendanceDateTime.getDayOfMonth(),
                    attendanceDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
            ));
        }
    }

    private void validateWeekend(LocalDateTime attendanceDateTime) {
        DayOfWeek attendanceDayOfWeek = attendanceDateTime.getDayOfWeek();
        if (attendanceDayOfWeek.equals(DayOfWeek.SATURDAY) || attendanceDayOfWeek.equals(DayOfWeek.SUNDAY)) {
            throw new IllegalArgumentException("%d월 %d일 %s은 등교일이 아닙니다.".formatted(
                    attendanceDateTime.getMonthValue(),
                    attendanceDateTime.getDayOfMonth(),
                    attendanceDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
            ));
        }
    }

    private void validateOperationTime(LocalDateTime attendanceDateTime) {
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        if (attendanceTime.isBefore(CAMPUS_OPEN_TIME) || attendanceTime.isAfter(CAMPUS_CLOSE_TIME)) {
            throw new IllegalArgumentException("%d시 %d분은 캠퍼스 운영 시간이 아닙니다.".formatted(
                    attendanceTime.getHour(),
                    attendanceTime.getMinute()
            ));
        }
    }

    public DayOfWeek calculateDayOfWeek() {
        return this.dateTime.getDayOfWeek();
    }

    public int calculateMinuteDifference(final LocalTime otherTime) {
        return (int)Duration.between(otherTime, dateTime)
                .toMinutes();
    }

    public LocalDateTime getLocalDateTime() {
        return dateTime;
    }

    public boolean isSameDate(AttendanceDateTime otherDateTime) {
        LocalDate attendanceDate = this.dateTime.toLocalDate();
        LocalDate otherDate = otherDateTime.getLocalDateTime().toLocalDate();
        return attendanceDate.equals(otherDate);
    }

    public boolean isThisDayInCurrentMonth(final int day) {
        int attendanceDay = this.dateTime.getDayOfMonth();
        if (attendanceDay == day) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceDateTime that = (AttendanceDateTime) o;
        return Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime);
    }
}
