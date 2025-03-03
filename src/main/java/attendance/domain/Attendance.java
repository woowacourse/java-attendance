package attendance.domain;

import static attendance.constant.ErrorMessage.INVALID_ATTEND_DATE;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class Attendance {
    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);

    private final LocalDate date;
    private LocalTime time;

    public Attendance(final LocalDate date, final LocalTime time) {
        this.date = date;
        this.time = time;
    }

    public static Attendance from(final LocalDate date, LocalTime time) {
        validateAvailableAttendDate(date);
        validateOperatingHour(time);
        return new Attendance(date, time);
    }

    public void updateTime(final LocalTime time) {
        validateOperatingHour(time);
        this.time = time;
    }

    public boolean isEqualToDate(final LocalDate date) {
        return this.date.equals(date);
    }

    public boolean isEqualToDateByAttendance(final Attendance attendance) {
        return isEqualToDate(attendance.date);
    }

    public AttendanceStatus checkAttendanceStatus() {
        LocalTime absenceThreshold = CampusOperatingRule.getAbsenceThreshold(isMonday());
        LocalTime lateThreshold = CampusOperatingRule.getLateThreshold(isMonday());
        return AttendanceStatus.of(time, absenceThreshold, lateThreshold);
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date, time);
    }

    private boolean isMonday() {
        return this.date.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    private static void validateAvailableAttendDate(final LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY ||
                date.isEqual(CHRISTMAS)) {
            throw new IllegalArgumentException(String.format(INVALID_ATTEND_DATE.getMessage(),
                    date.getMonth().getValue(),
                    date.getDayOfMonth(),
                    date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));
        }
    }

    private static void validateOperatingHour(final LocalTime time) {
        if (CampusOperatingRule.isOperatingHour(time)) {
            return;
        }
        throw new IllegalArgumentException();
    }
}

