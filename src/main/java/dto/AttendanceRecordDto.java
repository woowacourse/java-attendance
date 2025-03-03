package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class AttendanceRecordDto {
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;
    private final String attendanceStatus;

    public AttendanceRecordDto(final LocalDate attendanceDate, final LocalTime attendanceTime,
                               final String attendanceStatus) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = attendanceStatus;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public Optional<LocalTime> getAttendanceTime() {
        return Optional.ofNullable(attendanceTime);
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }
}
