package attendance.dto;

import attendance.domain.Attendance;

import java.time.LocalDateTime;

public record UpdateBeforeAttendanceResponse(
        LocalDateTime dateTime,
        String status
) {
    public static UpdateBeforeAttendanceResponse of(Attendance before) {
        return new UpdateBeforeAttendanceResponse(
                before.getDateTime(),
                before.getStatus().getMessage()
        );
    }
}
