package dto;

import domain.AttendanceRecord;
import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AttendanceRecordDto {
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;
    private final String attendanceStatus;

    private AttendanceRecordDto(final LocalDate attendanceDate, final LocalTime attendanceTime,
                                final String attendanceStatus) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = attendanceStatus;
    }

    public static AttendanceRecordDto from(final AttendanceRecord attendanceRecord) {
        return new AttendanceRecordDto(
                attendanceRecord.getAttendanceDate(),
                attendanceRecord.getAttendanceTime().orElse(null),
                convertAttendanceStatusToString(attendanceRecord.calculateAttendanceStatus())
        );
    }

    public static List<AttendanceRecordDto> from(final List<AttendanceRecord> attendanceRecords) {
        return attendanceRecords.stream()
                .map(AttendanceRecordDto::from)
                .collect(Collectors.toList());
    }

    public static String convertAttendanceStatusToString(final AttendanceStatus attendanceStatus) {
        if (attendanceStatus == AttendanceStatus.ABSENCE) {
            return "결석";
        }
        if (attendanceStatus == AttendanceStatus.LATE) {
            return "지각";
        }
        return "출석";
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
