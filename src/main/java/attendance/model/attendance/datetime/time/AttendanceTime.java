package attendance.model.attendance.datetime.time;

import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class AttendanceTime {

    private final LocalTime value;

    private AttendanceTime(LocalTime value) {
        this.value = value;
    }

    public static AttendanceTime policyApplied(
            final LocalTime value,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        validateCampusOperationTime(value, campusOperationPolicy);
        return new AttendanceTime(value);
    }

    public static AttendanceTime nullObject() {
        return new AttendanceTime(null);
    }

    private static void validateCampusOperationTime(
            final LocalTime value,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        if (!campusOperationPolicy.isOpenTime(value)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public boolean isBefore(final LocalTime time) {
        if (value == null) {
            throw new IllegalStateException("출석 시간이 존재하지 않습니다.");
        }
        return value.isBefore(time);
    }

    public Optional<LocalTime> getValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceTime that = (AttendanceTime) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "AttendanceTime{" +
                "value=" + value +
                '}';
    }
}
