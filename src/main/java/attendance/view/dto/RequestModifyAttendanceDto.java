package attendance.view.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record RequestModifyAttendanceDto(String nickname, LocalDate date, LocalTime time) {
    
}
