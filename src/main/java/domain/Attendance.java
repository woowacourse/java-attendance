package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private final Day day;
    private Boolean isLate = false;
    private Boolean isAbsent = false;
    private LocalTime attendanceTime;

    public Attendance(Day day, LocalTime attendanceTime) {
        this.day = day;
        this.attendanceTime = attendanceTime;
        updateStatus();
    }

    public Boolean getLate() {
        return isLate;
    }

    public Boolean getAbsent() {
        return isAbsent;
    }

    public Boolean isEqualTo(LocalDate date) {
        return day.isEqualTo(date);
    }

    private void updateStatus() {
        if (attendanceTime == null) {
            isAbsent = true;
            return;
        }
        isLate = day.isLate(attendanceTime);
        isAbsent = day.isAbsent(attendanceTime);
    }

    public AttendanceDto toDto() {
        LocalDate date = day.getDate();
        return new AttendanceDto(date, isLate, isAbsent, attendanceTime);
    }

    public void updateAttendanceTime(LocalTime attendanceTime) {
        this.attendanceTime = attendanceTime;
        updateStatus();
    }

}
