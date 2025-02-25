package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceChecker {

    private LocalDateTime localDateTime;
    private AttendanceStatus attendanceStatus;
    private static final int DEFAULT_TIME = 0;

    public AttendanceChecker(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        this.attendanceStatus = AttendanceStatus.calculateStatus(localDateTime);
    }

    public static AttendanceChecker makeDefaultValue(LocalDate localDate) {
        LocalDateTime dateTime = localDate.atTime(DEFAULT_TIME, DEFAULT_TIME);
        return new AttendanceChecker(dateTime);
    }

    public void modifyAttendanceTime(LocalDateTime localDateTime) {
        this.localDateTime = this.localDateTime.withHour(localDateTime.getHour())
                .withMinute(localDateTime.getMinute());
        this.attendanceStatus = AttendanceStatus.calculateStatus(localDateTime);
    }

    public boolean isAbsence() {
        return this.attendanceStatus.equals(AttendanceStatus.ABSENCE);
    }

    public boolean isLate() {
        return this.attendanceStatus.equals(AttendanceStatus.LATE);
    }

    public boolean isAttendance() {
        return this.attendanceStatus.equals(AttendanceStatus.ATTENDANCE);
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getAttendanceStatus() {
        return attendanceStatus.getName();
    }

}
