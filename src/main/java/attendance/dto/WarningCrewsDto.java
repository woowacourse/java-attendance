package attendance.dto;

import attendance.model.AttendanceReport;
import attendance.model.AttendanceWarning;
import attendance.model.Crew;
import attendance.model.Crews;
import attendance.model.CustomClock;
import java.time.LocalDate;
import java.util.List;

public record WarningCrewsDto(List<WarningCrewDetailDto> warningCrewDetailDTO) {

    public static WarningCrewsDto from(Crews crews, CustomClock clock, LocalDate trainingStartDate) {
        return new WarningCrewsDto(crews.getCrews().stream()
                .map(crew -> {
                    AttendanceReport report = new AttendanceReport(clock, crew.getAttendanceHistory(),
                            trainingStartDate);
                    return WarningCrewDetailDto.from(crew, report);
                })
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
        public static WarningCrewDetailDto from(Crew crew, AttendanceReport report) {
            return new WarningCrewDetailDto(
                    crew.getName(),
                    report.calculateAbsenceCount(),
                    report.calculateLateCount(),
                    report.calculateAbsenceCount(),
                    report.calculateWarning().name()
            );
        }
    }

}
