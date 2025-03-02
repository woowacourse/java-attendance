package domain;

import java.time.LocalDate;
import java.time.LocalTime;

import domain.attendance_time.AttendanceTime;
import domain.attendance_time.NoShowAttendanceTime;
import domain.attendance_time.ShowAttendanceTime;

public class Attendance {
    
    private final AttendanceDate attendDate;
    private final AttendanceTime attendTime;
    private final AttendanceStatus status;
    
    private Attendance(
            final AttendanceDate attendDate,
            final AttendanceTime attendTime,
            final AttendanceStatus status
    ) {
        this.attendDate = attendDate;
        this.attendTime = attendTime;
        this.status = status;
    }
    
    public static Attendance show(final LocalDate attendDate, final LocalTime attendTime) {
        return new Attendance(
                new AttendanceDate(attendDate),
                new ShowAttendanceTime(attendTime),
                AttendanceStatus.of(attendDate.getDayOfWeek(), attendTime)
        );
    }
    
    public static Attendance noShow(LocalDate noShowDate) {
        return new Attendance(
                new AttendanceDate(noShowDate),
                new NoShowAttendanceTime(),
                AttendanceStatus.결석
        );
    }
    
    public LocalDate getAttendDate() {
        return attendDate.getAttendDate();
    }
    
    public AttendanceTime getAttendTime() {
        return attendTime;
    }
    
    public AttendanceStatus getStatus() {
        return status;
    }
}
