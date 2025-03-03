package attendance.dto;

import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceReport;
import attendance.domain.Crew;
import attendance.domain.WoowaDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CrewHistoryDto(
        String crewName,
        List<AttendanceRecordDto> recordDtos,
        long presentCount,
        long lateCount,
        long absentCount,
        String warningStatus,
        List<LocalDate> noAttendanceDates) {

    public static CrewHistoryDto of(Crew crew, AttendanceReport report) {
        return new CrewHistoryDto(
                crew.getName(),
                report.getRecords().stream().map(AttendanceRecordDto::from).toList(),
                report.countPresent(),
                report.countLate(),
                report.countAbsent(),
                report.getWarningStatus().getTitle(),
                report.getNoAttendanceDates().stream().map(WoowaDate::toLocalDate).toList()
        );
    }

    public record AttendanceRecordDto(LocalDateTime attendanceDateTime, String AttendanceStatus) {
        public static AttendanceRecordDto from(AttendanceRecord record) {
            return new AttendanceRecordDto(
                    record.getDateTIme(),
                    record.getAttendanceStatus().getTitle()
            );
        }
    }
}

