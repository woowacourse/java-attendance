package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public interface Attendance {
    boolean isAttendedOn(LocalDate date);
    boolean isTimeRecorded();
    LocalDate getDate();
    LocalTime getTime();
    AttendanceStatus getStatus();
}
