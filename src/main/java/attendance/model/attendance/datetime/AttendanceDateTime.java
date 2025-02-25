package attendance.model.attendance.datetime;

import attendance.model.attendance.datetime.date.AttendanceDate;
import attendance.model.attendance.datetime.time.AttendanceTime;
import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class AttendanceDateTime {

    private final AttendanceDate date;
    private final AttendanceTime time;

    private AttendanceDateTime(AttendanceDate date, AttendanceTime time) {
        this.date = date;
        this.time = time;
    }

    public static AttendanceDateTime policyApplied(
            final LocalDateTime dateTime,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        return new AttendanceDateTime(
                AttendanceDate.policyApplied(dateTime.toLocalDate(), campusOperationPolicy),
                AttendanceTime.policyApplied(dateTime.toLocalTime(), campusOperationPolicy)
        );
    }

    public static AttendanceDateTime policyAppliedWithNullTime(
            final LocalDate date,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        return new AttendanceDateTime(
                AttendanceDate.policyApplied(date, campusOperationPolicy),
                AttendanceTime.nullObject()
        );
    }

    public boolean isBeforeTime(final LocalTime time) {
        return this.time.isBefore(time);
    }

    public boolean isSameDate(LocalDate date) {
        return false;
    }

    public LocalDate getDate() {
        return date.getValue();
    }

    public Optional<LocalTime> getTime() {
        return time.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceDateTime that = (AttendanceDateTime) o;
        return Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }

    @Override
    public String toString() {
        return "AttendanceDateTime{" +
                "date=" + date +
                ", time=" + time +
                '}';
    }
}
