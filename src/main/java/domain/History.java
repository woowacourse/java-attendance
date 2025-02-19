package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record History(
    LocalDate date,
    LocalTime time,
    AttendanceStatus status,
    boolean isChecked
) {
}
