package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private final Day day;
    private LocalTime attendanceTime;

    public Attendance(Day day, LocalTime attendanceTime) {
        validateDay(day);
        this.day = day;
        this.attendanceTime = attendanceTime;
    }

    private void validateDay(Day day) {
        if (day.checkHoliday()) {
            throw new IllegalArgumentException("[ERROR] 등교일이 아닙니다.");
        }
    }

    public Boolean isEqualTo(LocalDate date) {
        return day.isEqualTo(date);
    }

    public boolean isLate() {
        return day.isLate(attendanceTime);
    }

    public boolean isAbsent() {
        return day.isAbsent(attendanceTime);
    }

    public void updateAttendanceTime(LocalTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public Day getDay() {
        return day;
    }
}
