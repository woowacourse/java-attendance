package dto.requeset;

import java.time.LocalDate;
import java.time.LocalTime;

public record AttendanceModifyRequest(
        String name,
        LocalDate targetDate,
        LocalTime modifyTo
) {
}
