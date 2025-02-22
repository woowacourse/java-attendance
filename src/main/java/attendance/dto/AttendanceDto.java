package attendance.dto;

import attendance.model.AttendanceDetail;
import attendance.model.AttendanceReport;
import attendance.model.Crew;
import java.time.LocalDateTime;
import java.util.List;

public record AttendanceDto(
        String crewName,
        List<AttendanceDetailDto> attendanceDetailDtos,
        String warningType,
        long attendanceCount,
        long lateCount,
        long absenceCount
) {
    public static AttendanceDto from(Crew crew, AttendanceReport report) {
        return new AttendanceDto(
                crew.getName(),
                crew.getAttendanceHistory().stream().map(AttendanceDetailDto::from).toList(),
                report.calculateWarning().getTitle(),
                report.calculateAttendanceCount(),
                report.calculateLateCount(),
                report.calculateAbsenceCount()
        );
    }

    public record AttendanceDetailDto(
            LocalDateTime attendanceDateTime,
            String attendanceType
    ) {
        public static AttendanceDetailDto from(AttendanceDetail attendanceDetail) {
            return new AttendanceDetailDto(
                    attendanceDetail.getAttendanceDateTime(),
                    attendanceDetail.getAttendance().getTitle()
            );
        }
    }

}
