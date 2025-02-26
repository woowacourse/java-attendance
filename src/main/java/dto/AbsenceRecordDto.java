package dto;

import domain.AbsencePolicy;
import domain.Crew;

public record AbsenceRecordDto(Crew crew, int lateness, int absence, AbsencePolicy absencePolicy) {
}
