package vo;

import domain.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendResult(
        LocalDate attendDate,
        LocalTime attendTime,
        AttendanceStatus status
) {
}
