package io.dto;

import domain.ExpelWarning;

public record ExpelWarningCrewResponse(
        String nickname,
        int lateCount,
        int absentCount,
        ExpelWarning expelWarning
) {
}
