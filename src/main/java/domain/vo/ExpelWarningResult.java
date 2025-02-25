package domain.vo;

import domain.ExpelWarning;

public record ExpelWarningResult(
        ExpelWarning expelWarning,
        int lateCount,
        int absentCount
) {
}
