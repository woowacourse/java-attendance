package attendance.dto;

import attendance.model.AttendanceRecord;
import attendance.model.AttendanceRegister;
import attendance.model.SystemDuration;
import java.time.LocalDate;
import java.util.List;

public record WarningCrewsDto(List<WarningCrewDetailDTO> warningCrewDetailDTO) {

    public static WarningCrewsDto from(AttendanceRegister attendanceRegister) {
        return new WarningCrewsDto(
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
        public static WarningCrewDetailDTO of(String crewName, AttendanceRecord attendanceRecord) {
            LocalDate now = SystemDuration.getNow();
            return new WarningCrewDetailDTO(
                    crewName,
                    attendanceRecord.computeAbsencesUntil(now),
                    attendanceRecord.computeLateCount(),
                    2,
                    attendanceRecord.computePanaltyUntil(now).name()
            );
        }
    }

}
