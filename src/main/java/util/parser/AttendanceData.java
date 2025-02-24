package util.parser;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceData(
    String nickname,
    LocalDate date,
    LocalTime time
) {

}
