package domain.attendance;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static domain.attendance.AttendanceStatus.*;

public class AttendanceDate {
    private LocalDateTime attendanceAt;
    private AttendanceStatus status;

    public AttendanceDate(LocalDateTime attendanceAt) {
        this.attendanceAt = attendanceAt;
        this.status = calcAttendanceStatus(getDayOfWeek(), LocalTime.from(this.attendanceAt));
    }

    public DayOfWeek getDayOfWeek() {
        return Arrays.stream(DayOfWeek.values())
                .filter(dayOfWeek -> dayOfWeek.getValue() == attendanceAt.getDayOfWeek().getValue())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 요일을 찾을 수 없음"));
    }

    public void editLocalDate(LocalDateTime editLocalDateTime){
        this.attendanceAt = editLocalDateTime;
        this.status = calcAttendanceStatus(getDayOfWeek(), LocalTime.from(this.attendanceAt));
    }

    public boolean isAttendance() {
        return this.status == ATTENDANCE;
    }

    public boolean isTardy(){
        return this.status == TARDY;
    }

    public boolean isAbsence(){
        return this.status == ABSENCE;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public LocalDateTime getAttendanceAt() {
        return attendanceAt;
    }
}
