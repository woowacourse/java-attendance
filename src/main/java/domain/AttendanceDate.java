package domain;

import domain.policy.attend.AttendancePolicy;
import util.FormatUtil;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class AttendanceDate {
    private final LocalDate date;

    private AttendanceDate(
            LocalDate date,
            AttendancePolicy attendancePolicy
    ) {
        validate(date, attendancePolicy);
        this.date = date;
    }

    public static AttendanceDate of(LocalDate date,
                                    AttendancePolicy attendancePolicy) {
        return new AttendanceDate(date, attendancePolicy);
    }

    private void validate(LocalDate date, AttendancePolicy attendancePolicy) {
        if (attendancePolicy.canAttendDate(date)) {
            return;
        }
        throw new IllegalArgumentException(String.format("%s %s은 등교일이 아닙니다.",
                date.format(FormatUtil.DATE_FORMATTER_KOREAN),
                date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));
    }

    public LocalDate toLocalDate() {
        return date;
    }

    public LocalDate date() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceDate that = (AttendanceDate) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }
}
