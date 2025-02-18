package dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class AttendanceResultDTO {
    
    private LocalDateTime attendanceTime;
    private String attendanceStatus;
    
    public AttendanceResultDTO(LocalDateTime attendanceTime, String attendanceStatus) {
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = attendanceStatus;
    }
    
    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }
    
    public String getAttendanceStatus() {
        return attendanceStatus;
    }
    
    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof AttendanceResultDTO that)) return false;
        
        return Objects.equals(getAttendanceTime(), that.getAttendanceTime()) && Objects.equals(getAttendanceStatus(), that.getAttendanceStatus());
    }
    
    @Override
    public int hashCode() {
        int result = Objects.hashCode(getAttendanceTime());
        result = 31 * result + Objects.hashCode(getAttendanceStatus());
        return result;
    }
    
    @Override
    public String toString() {
        return "AttendanceResultDTO{" +
                "attendanceTime=" + attendanceTime +
                ", attendanceStatus='" + attendanceStatus + '\'' +
                '}';
    }
}
