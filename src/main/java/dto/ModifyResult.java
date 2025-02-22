package dto;

import domain.Attendance;
import java.time.LocalDateTime;

public class ModifyResult {
    private final Attendance originalAttendance;
    private final Attendance newAttendance;

    public ModifyResult(Attendance originalAttendance, Attendance newAttendance) {
        this.originalAttendance = originalAttendance;
        this.newAttendance = newAttendance;
    }

    public LocalDateTime getOriginalDateAndTime() {
        return originalAttendance.dateAndTime();
    }

    public LocalDateTime getNewDateAndTime() {
        return newAttendance.dateAndTime();
    }
}
