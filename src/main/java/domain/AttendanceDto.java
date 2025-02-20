package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceDto {
    private LocalDate date;
    private Boolean isLate;
    private Boolean isAbsent;
    private LocalTime attendanceTime;

    public AttendanceDto(LocalDate date, Boolean isLate, Boolean isAbsent, LocalTime attendanceTime) {
        this.date = date;
        this.isLate = isLate;
        this.isAbsent = isAbsent;
        this.attendanceTime = attendanceTime;
    }

    public LocalDate getDate() {
        return date;
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
