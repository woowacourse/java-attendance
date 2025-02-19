package attendance.dto;

import attendance.model.AttendanceDetail;
import attendance.model.AttendanceWarning;
import attendance.model.Crew;
import java.time.LocalDateTime;
import java.util.List;

public record AttendanceDTO(
        String crewName,
        List<AttendanceDetailDTO> attendanceDetailDTOs,
        String warningType,
        long attendanceCount,
        long lateCount,
        long absenceCount
) {
    public static AttendanceDTO from(Crew crew) {
        return new AttendanceDTO(
                crew.getName(),
                crew.getAttendanceHistory().stream().map(AttendanceDetailDTO::from).toList(),
                AttendanceWarning.from(crew.getAttendanceHistory().getAbsenceCount()).name(),
                crew.getAttendanceHistory().getAttendanceCount(),
                crew.getAttendanceHistory().getTotalLateCount(),
                crew.getAttendanceHistory().getTotalAbsenceCount()
        );
    }

    public record AttendanceDetailDTO(
            LocalDateTime attendanceDateTime,
            String attendanceType
    ) {
        public static AttendanceDetailDTO from(AttendanceDetail attendanceDetail) {
            return new AttendanceDetailDTO(
                    attendanceDetail.getLocalDateTime(),
                    attendanceDetail.getAttandence().name()
            );
        }
    }
}
