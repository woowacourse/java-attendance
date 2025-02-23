package util.parser;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceRecord(
    String nickname,
    LocalDate date,
    LocalTime time
) {

}
