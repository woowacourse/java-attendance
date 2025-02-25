package attendance.dto;

import attendance.model.AttendanceHistory;
import attendance.model.AttendanceRegister;
import java.util.List;

public record WarningCrewsDTO(List<WarningCrewDetailDTO> warningCrewDetailDTO) {

    public static WarningCrewsDTO from(AttendanceRegister attendanceRegister) {
        return new WarningCrewsDTO(
                attendanceRegister.entryStream()
                        .map(entry -> WarningCrewDetailDTO.of(entry.getKey(), entry.getValue()))
                        .toList()
        );
    }

    public record WarningCrewDetailDTO(
            String crewName,
            long absenceCount,
            long lateCount,
            long convertLateCount,
            String warningType
    ) {
        public static WarningCrewDetailDTO of(String crewName, AttendanceHistory attendanceHistory) {
            return new WarningCrewDetailDTO(
                    crewName,
                    attendanceHistory.computeAbsenceCount(),
                    attendanceHistory.computeLateCount(),
                    attendanceHistory.convertLateCount(),
                    attendanceHistory.getAttendanceWarning().name()
            );
        }
    }

}
