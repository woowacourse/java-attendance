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
        return attendanceDateTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public boolean checkSameDate(LocalDate attendanceDate) {
        return this.attendanceDateTime.toLocalDate().equals(attendanceDate);
    }

    public void updateAttendanceDateTime(LocalTime newTime) {
        this.attendanceDateTime = LocalDateTime.of(attendanceDateTime.getYear(), attendanceDateTime.getMonth(),
                attendanceDateTime.getDayOfMonth(), newTime.getHour(), newTime.getMinute());
        this.attendanceStatus = AttendanceStatus.findStatus(attendanceDateTime);
    }
}
