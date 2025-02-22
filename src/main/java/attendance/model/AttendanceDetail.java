package attendance.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceDetail {

    private final WoowaDate woowaDate;
    private LocalTime attendanceTime;
    private Attendance attendance;

    public AttendanceDetail(WoowaDate woowaDate, LocalTime localTime) {
        this.woowaDate = woowaDate;
        this.attendanceTime = localTime;
        this.attendance = Attendance.from(woowaDate, attendanceTime);
    }

    public void modify(LocalTime localTime) {
        this.attendanceTime = localTime;
        this.attendance = Attendance.from(woowaDate, localTime);
    }

    public Attendance getAttendance() {
        return attendance;
    }


    public LocalDateTime getAttendanceDateTime() {
        return LocalDateTime.of(woowaDate.getLocalDate(), attendanceTime);
    }

    public WoowaDate getAttendanceDate() {
        return woowaDate;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public boolean isSameAs(Attendance attendance) {
        return attendance == this.attendance;
    }

}
