package dto;

import java.time.LocalDateTime;

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
}
