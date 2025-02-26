package dto;

import java.time.LocalDateTime;

public record AttendanceRecordDto(
        String nickname,
        LocalDateTime attendanceDateTime
) {

}
    
