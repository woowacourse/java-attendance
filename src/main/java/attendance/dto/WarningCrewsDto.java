package attendance.dto;

import attendance.model.AttendanceWarning;
import attendance.model.Crew;
import attendance.model.Crews;
import java.util.List;

public record WarningCrewsDto(List<WarningCrewDetailDto> warningCrewDetailDTO) {

    public static WarningCrewsDto from(Crews crews) {
        return new WarningCrewsDto(crews.getCrews().stream().map(WarningCrewDetailDto::from)
                .filter(dto -> !dto.warningType.equals(AttendanceWarning.NONE.name()))
                .toList());
    }

    public record WarningCrewDetailDto(
            String crewName,
            long absenceCount,
            long lateCount,
            long convertLateCount,
            String warningType
    ) {
        public static WarningCrewDetailDto from(Crew crew) {
            return new WarningCrewDetailDto(
                    crew.getName(),
                    crew.getAttendanceHistory().getAbsenceCount(),
                    crew.getAttendanceHistory().getLateCount(),
                    crew.getAttendanceHistory().getLateCount() + crew.getAttendanceHistory().getAbsenceCount() * 3,
                    AttendanceWarning.from(crew).name());
        }
    }

}
