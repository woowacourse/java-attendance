package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceTime {

    private LocalDateTime attendanceDateTime;
    private AttendanceStatus attendanceStatus;

    public AttendanceTime(LocalDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = AttendanceStatus.findStatus(attendanceDateTime);
    }

    public AttendanceTime(LocalDate attendanceDate, AttendanceStatus attendanceStatus) {
        this.attendanceDateTime = attendanceDate.atTime(0, 0);
        this.attendanceStatus = attendanceStatus;
    }

    public LocalDateTime getAttendanceDateTime() {
        return this.attendanceDateTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        return this.attendanceStatus;
    }

    public boolean checkAttended(LocalDate attendanceDate) {
        return this.attendanceDateTime.toLocalDate().equals(attendanceDate) && !this.attendanceStatus.equals(AttendanceStatus.UNATTEND);
    }

    public void updateAttendanceDateTime(LocalTime newTime) {
        this.attendanceDateTime = LocalDateTime.of(this.attendanceDateTime.getYear(), this.attendanceDateTime.getMonth(),
                this.attendanceDateTime.getDayOfMonth(), newTime.getHour(), newTime.getMinute());
        this.attendanceStatus = AttendanceStatus.findStatus(this.attendanceDateTime);
    }
}
