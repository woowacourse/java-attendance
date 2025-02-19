package domain;

import java.time.LocalTime;

public class AttendanceDto {

    private Boolean isLate;
    private Boolean isAbsent;
    private LocalTime attendanceTime;

    public AttendanceDto(Boolean isLate, Boolean isAbsent, LocalTime attendanceTime) {
        this.isLate = isLate;
        this.isAbsent = isAbsent;
        this.attendanceTime = attendanceTime;
    }

    public Boolean getLate() {
        return isLate;
    }

    public Boolean getAbsent() {
        return isAbsent;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }
}
