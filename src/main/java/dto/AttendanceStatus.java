package dto;

import domain.AbsencePolicy;

public record AttendanceStatus(
        AbsenceHistoryDto absenceHistory,
        AbsencePolicy absencePolicy) {
}
