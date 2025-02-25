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

        return null;
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
