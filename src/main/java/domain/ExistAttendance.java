package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ExistAttendance implements Attendance {
    private final AttendanceDate date;
    private final AttendanceTime time;
    private final AttendanceStatus status;

    public ExistAttendance(LocalDate date, LocalTime time) {
        this.date = new AttendanceDate(date);
        this.time = new AttendanceTime(time);
        LocalTime startTime = EducationTime.startOf(date.getDayOfWeek());
        this.status = AttendanceStatus.from(startTime, time);
    }

    public boolean isAttendedOn(LocalDate targetDate) {
        return date.getValue().isEqual(targetDate);
    }

    @Override
    public boolean isTimeRecorded() {
        return true;
    }

    public LocalTime getTime() {
        return time.getValue();
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ExistAttendance that = (ExistAttendance) object;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
