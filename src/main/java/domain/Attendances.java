package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Attendances {
    private final List<LocalDateTime> attendances;

    public Attendances(List<LocalDateTime> attendances) {
        this.attendances = attendances;
    }

    public Attendances add(LocalDateTime attendanceTime) {
        this.attendances.add(attendanceTime);
        return this;
    }

    public boolean haveAttendanceDate(LocalDate attendanceDate) {
        return attendances.stream()
                .map(LocalDateTime::toLocalDate)
                .anyMatch(attendanceDate::isEqual);
    }
}
