package attendance.model.attendance;

import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class AttendanceLog {

    private final LocalDate date;
    private final LocalTime time;
    private final AttendanceStatus attendanceStatus;

    private AttendanceLog(LocalDate date, LocalTime time, AttendanceStatus attendanceStatus) {
        this.date = date;
        this.time = time;
        this.attendanceStatus = attendanceStatus;
    }

    public static AttendanceLog fromAttendanceDateTime(
            final LocalDateTime attendanceDateTime,
            final CampusOperationPolicy campusOperationPolicy
    ) {

        return null;
    }

    public static AttendanceLog fromAbsenceDate(final LocalDate absenceDate) {
        return null;
    }

    public LocalDate getDate() {
        return date;
    }

    public Optional<LocalTime> getTime() {
        return Optional.ofNullable(time);
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceLog that = (AttendanceLog) o;
        return Objects.equals(date, that.date) && Objects.equals(time, that.time)
                && attendanceStatus == that.attendanceStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, attendanceStatus);
    }

    @Override
    public String toString() {
        return "AttendanceLog{" +
                "date=" + date +
                ", time=" + time +
                ", attendanceStatus=" + attendanceStatus +
                '}';
    }
}
