package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceTime {

    private LocalDateTime attendanceDateTime;
    private AttendanceStatus attendanceStatus;

    public AttendanceTime(LocalDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = AttendanceStatus.findStatus(attendanceDateTime);
    }

    public boolean checkSameDate(LocalDate attendanceDate) {
        return this.attendanceDateTime.toLocalDate().equals(attendanceDate);
    }
}
