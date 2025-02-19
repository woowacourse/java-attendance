package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public record CrewResponse(
        String name,
        Map<LocalDate, LocalTime> attendanceBook
) {
}
