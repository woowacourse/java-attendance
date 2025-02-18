package dto;

import java.time.LocalDateTime;

public class AttendanceResultDTO {
    
    private String name;
    private LocalDateTime attendanceTime;
    private String attendanceStatus;
    
    public AttendanceResultDTO(String name, LocalDateTime attendanceTime, String attendanceStatus) {
        this.name = name;
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = attendanceStatus;
    }
    
    public String getName() {
        return name;
    }
    
    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }
    
    public String getAttendanceStatus() {
        return attendanceStatus;
    }
}
