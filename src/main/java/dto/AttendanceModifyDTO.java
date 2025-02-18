package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceModifyDTO {
    
    private String name;
    private LocalDate attendanceDate;
    private LocalTime oldAttendanceTime;
    private String oldAttendanceStatus;
    private LocalTime newAttendanceTime;
    private String newAttendanceStatus;
    
    public AttendanceModifyDTO(String name, LocalDate attendanceDate, LocalTime oldAttendanceTime, String oldAttendanceStatus, LocalTime newAttendanceTime, String newAttendanceStatus) {
        this.name = name;
        this.attendanceDate = attendanceDate;
        this.oldAttendanceTime = oldAttendanceTime;
        this.oldAttendanceStatus = oldAttendanceStatus;
        this.newAttendanceTime = newAttendanceTime;
        this.newAttendanceStatus = newAttendanceStatus;
    }
    
    public String getName() {
        return name;
    }
    
    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }
    
    public LocalTime getOldAttendanceTime() {
        return oldAttendanceTime;
    }
    
    public String getOldAttendanceStatus() {
        return oldAttendanceStatus;
    }
    
    public LocalTime getNewAttendanceTime() {
        return newAttendanceTime;
    }
    
    public String getNewAttendanceStatus() {
        return newAttendanceStatus;
    }
}
