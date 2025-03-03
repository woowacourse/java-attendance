package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AttendanceChecker {
    void checkCampusOpen(LocalDate date, LocalTime time);

    boolean isCampusOpenDate(LocalDate date);
}
