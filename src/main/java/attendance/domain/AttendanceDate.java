package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record AttendanceDate(LocalDate attendanceDate) implements Comparable<AttendanceDate> {

    private static final DateTimeFormatter MONTH_DAY_DAY_OF_WEEK =
            DateTimeFormatter.ofPattern("M월 d일 E요일", Locale.KOREA);

    public AttendanceDate {
        validateCampusHoliday(attendanceDate);
    }

    private void validateCampusHoliday(final LocalDate attendanceDate) {
        if (isCampusHoliday(attendanceDate)) {
            throw new IllegalArgumentException(
                    String.join("", MONTH_DAY_DAY_OF_WEEK.format(attendanceDate), "은 등교일이 아닙니다.")
            );
        }
    }

    private boolean isCampusHoliday(final LocalDate attendanceDate) {
        return CampusHoliday.isCampusClosingDay(attendanceDate) || CampusHoliday.isExistsInPublicHolidays(attendanceDate);
    }

    public boolean isSameDate(final LocalDate localDate) {
        return attendanceDate.equals(localDate);
    }

    public boolean isBeforeOrEqualDate(final LocalDate localDate) {
        return isSameDate(localDate) || attendanceDate.isBefore(localDate);
    }

    public boolean isMonday() {
        return attendanceDate.getDayOfWeek().equals(DayOfWeek.MONDAY);
    }

    @Override
    public int compareTo(final AttendanceDate o) {
        return this.attendanceDate.compareTo(o.attendanceDate);
    }

}
