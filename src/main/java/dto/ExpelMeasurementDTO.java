package dto;

import java.util.Objects;

public class ExpelMeasurementDTO {
    
    private String targetName;
    private int lateCount;
    private int absentCount;
    private String measurementName;
    
    public ExpelMeasurementDTO(String targetName, int lateCount, int absentCount, String measurementName) {
        this.targetName = targetName;
        this.lateCount = lateCount;
        this.absentCount = absentCount;
        this.measurementName = measurementName;
    }
    
    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ExpelMeasurementDTO that)) return false;
        
        return lateCount == that.lateCount && absentCount == that.absentCount && Objects.equals(targetName, that.targetName) && Objects.equals(measurementName, that.measurementName);
    }
    
    @Override
    public int hashCode() {
        int result = Objects.hashCode(targetName);
        result = 31 * result + lateCount;
        result = 31 * result + absentCount;
        result = 31 * result + Objects.hashCode(measurementName);
        return result;
    }
    
    @Override
    public String toString() {
        return "ExpelMeasurementDTO{" +
                "targetName='" + targetName + '\'' +
                ", lateCount=" + lateCount +
                ", absentCount=" + absentCount +
                ", measurementName='" + measurementName + '\'' +
                '}';
    }
}
