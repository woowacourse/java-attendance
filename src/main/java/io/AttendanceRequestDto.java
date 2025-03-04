package io;

import java.time.LocalDateTime;

public record AttendanceRequestDto(
        String crewName,
        LocalDateTime attendAt
) {
}
