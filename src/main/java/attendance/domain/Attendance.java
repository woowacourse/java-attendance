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

    private LocalDateTime dateTime;

    public Attendance(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static Attendance from(final LocalDateTime dateTime) {
        validateAvailableAttendDate(dateTime.toLocalDate());
        validateOperatingHour(dateTime.toLocalTime());
        return new Attendance(dateTime);
    }

    public void updateTime(final LocalTime time) {
        this.dateTime = this.dateTime.with(time);
    }

    public boolean isEqualToDate(final LocalDate date) {
        return this.dateTime.toLocalDate().equals(date);
    }

    public boolean isEqualToDateByAttendance(final Attendance attendance) {
        return isEqualToDate(attendance.dateTime.toLocalDate());
    }

    public AttendanceStatus checkAttendanceStatus() {
        LocalTime absenceThreshold = CampusOperatingRule.getAbsenceThreshold(isMonday());
        LocalTime lateThreshold = CampusOperatingRule.getLateThreshold(isMonday());
        return AttendanceStatus.of(dateTime.toLocalTime(), absenceThreshold, lateThreshold);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    private boolean isMonday() {
        return this.dateTime.getDayOfWeek() == DayOfWeek.MONDAY;
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

