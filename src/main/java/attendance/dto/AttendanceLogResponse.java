package attendance.dto;

import attendance.model.domain.attendance.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class AttendanceLogResponse {

    private final TimeNullableDateTimeResponse timeNullableDateTimeResponse;
    private final AttendanceStatusResponse attendanceStatusResponse;

    private AttendanceLogResponse(TimeNullableDateTimeResponse timeNullableDateTimeResponse,
                                  AttendanceStatusResponse attendanceStatusResponse) {
        this.timeNullableDateTimeResponse = timeNullableDateTimeResponse;
        this.attendanceStatusResponse = attendanceStatusResponse;
    }

    public static AttendanceLogResponse of(TimeNullableDateTimeResponse timeNullableDateTimeResponse,
                                           AttendanceStatus attendanceStatus) {
        return new AttendanceLogResponse(timeNullableDateTimeResponse, AttendanceStatusResponse.from(attendanceStatus));
    }

    public LocalDate getDate() {
        return timeNullableDateTimeResponse.getDate();
    }

    public Optional<LocalTime> getTime() {
        return timeNullableDateTimeResponse.getTime();
    }

    public Optional<LocalDateTime> getDateTime() {
        return timeNullableDateTimeResponse.getDateTime();
    }

    public String getAttendanceStatus() {
        return attendanceStatusResponse.getAttendanceStatus();
    }
}
