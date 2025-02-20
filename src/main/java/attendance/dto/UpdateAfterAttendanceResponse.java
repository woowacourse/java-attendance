package attendance.dto;

import attendance.domain.Attendance;

import java.time.LocalDateTime;

public record UpdateAfterAttendanceResponse(
        LocalDateTime dateTime,
        String status
) {
    public static UpdateAfterAttendanceResponse of(Attendance after) {
        return new UpdateAfterAttendanceResponse(
                after.getDateTime(),
                after.getStatus().getMessage()
        );
    }
}
