package controller.dto;

import domain.crew.Crew;
import dto.CrewDto;

public class CrewDtoConverter {

    public static CrewDto toDto(Crew crew) {
        return new CrewDto(crew.getName());
    }
}
