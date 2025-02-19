package domain;

import java.util.Comparator;
import java.util.List;

public class CrewDtos {
    private final List<CrewDto> crewDtos;

    public CrewDtos(List<CrewDto> crewDtos) {
        this.crewDtos = crewDtos;
    }

    private void sortCrewDtos() {
        crewDtos.sort(
                Comparator.comparing((CrewDto dto) -> dto.getPenaltyStatus().getThreshold())
                        .thenComparing(dto -> dto.getLateCount() + dto.getAbsentCount())
                        .thenComparing(CrewDto::getNickName, Comparator.reverseOrder()));
    }

    public List<CrewDto> getCrewDtos() {
        sortCrewDtos();
        return crewDtos;
    }

}
