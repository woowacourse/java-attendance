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

    public Attendance(Attendance attendance) {
        this.day = attendance.day;
        this.attendanceTime = attendance.attendanceTime;
        this.isLate = attendance.isLate;
        this.isAbsent = attendance.isAbsent;
    }

    public Day getDay() {
        return new Day(day.getDate());
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

    public void updateAttendanceTime(LocalTime attendanceTime) {
        this.attendanceTime = attendanceTime;
        updateStatus();
    }

}
