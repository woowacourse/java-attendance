package attendance.dto;

import attendance.model.AttendanceWarning;
import attendance.model.Crew;
import attendance.model.Crews;
import java.util.List;

public record WarningCrewsDTO(List<WarningCrewDetailDTO> warningCrewDetailDTO) {

    public static WarningCrewsDTO from(Crews crews) {
        return new WarningCrewsDTO(crews.getCrews().stream().map(WarningCrewDetailDTO::from)
                .filter(dto -> !dto.warningType.equals(AttendanceWarning.해당없음.name()))
                .toList());
    }

    public record WarningCrewDetailDTO(
            String crewName,
            long absenceCount,
            long lateCount,
            String warningType
    ) {
        public static WarningCrewDetailDTO from(Crew crew) {
            return new WarningCrewDetailDTO(
                    crew.getName(),
                    crew.getAttendanceHistory().getTotalAbsenceCount(),
                    crew.getAttendanceHistory().getTotalLateCount(),
                    AttendanceWarning.from(crew).name());
        }
    }

}
