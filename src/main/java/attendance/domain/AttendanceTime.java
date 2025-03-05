package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class AttendanceTime {
    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;
    private final AttendanceStatus attendanceStatus;

    public AttendanceTime(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = null;
        this.attendanceStatus = AttendanceStatus.ABSENCE;
    }

    public AttendanceTime(LocalDateTime attendanceDateTime) {
        this.attendanceDate = attendanceDateTime.toLocalDate();
        this.attendanceTime = attendanceDateTime.toLocalTime();
        this.attendanceStatus = AttendanceStatus.fetchUserAttendanceStatus(attendanceDateTime);
    }

    public boolean isSameDateTime(final LocalDateTime inputTime) {
        return attendanceDate.getDayOfMonth() == inputTime.getDayOfMonth();
    }

    public boolean isAttendance() {
        return this.attendanceStatus.equals(AttendanceStatus.ATTENDANCE);
    }

    public boolean isLate() {
        return this.attendanceStatus.equals(AttendanceStatus.LATE);
    }

    public boolean isAbsence() {
        return this.attendanceStatus.equals(AttendanceStatus.ABSENCE);
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public Optional<LocalTime> getAttendanceTime() {
        return Optional.ofNullable(attendanceTime);
    }

    public AttendanceTime modifyAttendanceTime(LocalDateTime inputTime) {
        return new AttendanceTime(inputTime);
    }

}
