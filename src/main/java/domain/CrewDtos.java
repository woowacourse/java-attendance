package domain;

import java.util.Comparator;
import java.util.List;

public class CrewDtos {
    private List<CrewDto> crewDtos;

    public CrewDtos(List<CrewDto> crewDtos) {
        this.crewDtos = crewDtos;
    }

    private void sortCrewDtos() {
        crewDtos.sort(
                Comparator.comparing((CrewDto dto) -> dto.getPenaltyStatus().getThreshold(), Comparator.reverseOrder())
                        .thenComparing(dto -> dto.getLateCount() + dto.getAbsentCount(), Comparator.reverseOrder())
                        .thenComparing(CrewDto::getNickName));
    }

    public List<CrewDto> getCrewDtos() {
        sortCrewDtos();
        return crewDtos;
    }

}
