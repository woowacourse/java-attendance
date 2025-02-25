package attendance.model.attendance.datetime.date;

import attendance.model.campus.CampusOperationPolicy;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class AttendanceDate {

    private final LocalDate value;

    private AttendanceDate(LocalDate value) {
        this.value = value;
    }

    public static AttendanceDate policyApplied(
            final LocalDate value,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        validateCampusOperationTime(value, campusOperationPolicy);
        return new AttendanceDate(value);
    }

    private static void validateCampusOperationTime(
            final LocalDate value,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        if (!campusOperationPolicy.isOpenDate(value)) {
            throw new IllegalArgumentException("캠퍼스 운영 일자가 아닙니다.");
        }
    }

    public boolean isSameDate(final LocalDate date) {
        return this.value.equals(date);
    }

    public boolean isSameDayOfWeek(final DayOfWeek dayOfWeek) {
        return value.getDayOfWeek().equals(dayOfWeek);
    }

    public LocalDate getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceDate that = (AttendanceDate) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "AttendanceDate{" +
                "value=" + value +
                '}';
    }
}
