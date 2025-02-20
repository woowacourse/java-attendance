package attendance.domain;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class AttendanceDate {

    private final LocalDate attendanceDate;

    public AttendanceDate(final LocalDate attendanceDate) {
        validateHoliday(attendanceDate);
        this.attendanceDate = attendanceDate;
    }

    private void validateHoliday(final LocalDate attendanceDate) {
        if (Holiday.isExists(attendanceDate) || isWeekend(attendanceDate)) {
            throw new IllegalArgumentException("%d월 %d일 %s은 등교일이 아닙니다.".formatted(
                    attendanceDate.getMonthValue(), attendanceDate.getDayOfMonth(),
                    attendanceDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
            ));
        }
    }

    private boolean isWeekend(final LocalDate attendanceDate) {
        DayOfWeek attendanceDayOfWeek = attendanceDate.getDayOfWeek();
        return attendanceDayOfWeek.equals(SUNDAY) || attendanceDayOfWeek.equals(SATURDAY);
    }

    public boolean isSameDate(final LocalDate otherDate) {
        return this.attendanceDate.equals(otherDate);
    }

    public boolean isMonday() {
        return this.attendanceDate.getDayOfWeek().equals(MONDAY);
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceDate that)) {
            return false;
        }
        return Objects.equals(getAttendanceDate(), that.getAttendanceDate());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAttendanceDate());
    }

}
