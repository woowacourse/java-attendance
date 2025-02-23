package dto;

import domain.AbsencePolicy;

public record AbsenceResultDto(int attendance, int lateness, int absence, AbsencePolicy status) {
}
