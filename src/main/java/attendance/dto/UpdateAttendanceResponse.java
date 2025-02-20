package attendance.dto;

import attendance.model.domain.attendance.AttendanceStatus;
import java.time.LocalDateTime;

public class UpdateAttendanceResponse {

    private final LocalDateTime previousDateTime;
    private final LocalDateTime updatedDateTime;
    private final String previousStatus;
    private final String updatedStatus;

    private UpdateAttendanceResponse(
            LocalDateTime previousDateTime,
            LocalDateTime updatedDateTime,
            String previousStatus,
            String updatedStatus
    ) {
        this.previousDateTime = previousDateTime;
        this.updatedDateTime = updatedDateTime;
        this.previousStatus = previousStatus;
        this.updatedStatus = updatedStatus;
    }

    public static UpdateAttendanceResponse of(
            LocalDateTime before,
            LocalDateTime after,
            AttendanceStatus beforeStatus,
            AttendanceStatus afterStatus
    ) {
        return new UpdateAttendanceResponse(before, after, beforeStatus.getName(), afterStatus.getName());
    }

    public LocalDateTime getPreviousDateTime() {
        return previousDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public String getUpdatedStatus() {
        return updatedStatus;
    }
}
