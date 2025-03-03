package dto;

import domain.AttendanceAnalyze;
import domain.Username;
import java.util.List;
import java.util.Map;

public record ExpulsionCrewsDto(
        List<ExpulsionCrewDto> crews
) {
    public static ExpulsionCrewsDto from(Map<Username, AttendanceAnalyze> crewsInfo) {
        List<ExpulsionCrewDto> crews = crewsInfo.entrySet().stream()
                .map(entry -> ExpulsionCrewDto.of(entry.getKey(), entry.getValue()))
                .toList();
        return new ExpulsionCrewsDto(crews);
    }
}
