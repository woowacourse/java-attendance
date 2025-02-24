package dto.result;

public record ExpelMeasurementResult(
        String targetName,
        int lateCount,
        int absentCount,
        String measurementName
) {
}
