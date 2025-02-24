package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance implements Comparable<Attendance> {

    private final LocalDateTime dateTime;
    private final AttendanceStateType state;

    public Attendance(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.state = AttendanceStateType.find(EducationTime.calculateOverTime(dateTime));
    }

    public boolean isSameDate(final LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }

    public boolean isAlreadyChecked() {
        return !dateTime.toLocalTime().equals(LocalTime.MAX);
    }

    public boolean hasState(final AttendanceStateType state) {
        return this.state == state;
    }

    @Override
    public int compareTo(final Attendance other) {
        return dateTime.compareTo(other.dateTime);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AttendanceStateType getState() {
        return state;
    }
}
