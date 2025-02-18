package domain;

import java.time.LocalTime;

public class Attendance {
    private Day day;
    private Boolean isLate = false;
    private Boolean isAbsent = false;
    private LocalTime attendanceTime;

    public Attendance(Day day, LocalTime attendanceTime) {
        this.day = day;
        this.attendanceTime = attendanceTime;
        updateStatus();
    }

    private void updateStatus() {
        isLate = isLate();
        isAbsent = isAbsent();
    }

    private boolean isLate() {
        return day.isLate(attendanceTime);
    }

    private boolean isAbsent() {
        return day.isAbsent(attendanceTime);
    }

    public AttendanceDto toDto() {
        return new AttendanceDto(isLate, isAbsent);
    }


}
