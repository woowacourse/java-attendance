package dto.result;

import domain.ExpelRisk;

public record ExpelMeasurementResult(
        String targetName,
        int lateCount,
        int absentCount,
        ExpelRisk expelRisk
) {
}
