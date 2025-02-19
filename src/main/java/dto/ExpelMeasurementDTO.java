package dto;

public record ExpelMeasurementDTO(
        String targetName,
        int lateCount,
        int absentCount,
        String measurementName
) {
}
