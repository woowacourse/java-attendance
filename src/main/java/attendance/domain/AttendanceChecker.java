package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.domain.constant.Weekday;
import java.time.LocalDateTime;

public class AttendanceChecker {

    private LocalDateTime localDateTime;
    private AttendanceStatus attendanceStatus;

    private AttendanceChecker(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        this.attendanceStatus = calculateStatus();
    }

    public static AttendanceChecker of(LocalDateTime localDateTime) {
        return new AttendanceChecker(localDateTime);
    }

    public static AttendanceChecker makeDefaultValue(int year, int month, int day) {
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0);
        return new AttendanceChecker(dateTime);
    }

    public void modifyAttendanceTime(LocalDateTime localDateTime) {
        this.localDateTime = this.localDateTime.withHour(localDateTime.getHour())
                .withMinute(localDateTime.getMinute());
        this.attendanceStatus = calculateStatus();
    }

    public int isAbsence() {
        if (this.attendanceStatus.equals(AttendanceStatus.ABSENCE)) {
            return 1;
        }
        return 0;
    }
    public int isLate() {
        if (this.attendanceStatus.equals(AttendanceStatus.LATE)) {
            return 1;
        }
        return 0;
    }
    public int isAttendance() {
        if (this.attendanceStatus.equals(AttendanceStatus.ATTENDANCE)) {
            return 1;
        }
        return 0;
    }

    private AttendanceStatus calculateStatus() {
        if (checkDefault()) {
            return AttendanceStatus.ABSENCE;
        }
        if (checkHoliday()) {
            return AttendanceStatus.HOLIDAY;
        }
        if (checkMonday()) {
            return checkAttendanceStatus("13");
        }
        return checkAttendanceStatus("10");
    }

    private boolean checkMonday() {
        return Weekday.from(localDateTime.getDayOfWeek()).equals(Weekday.MONDAY);
    }

    private boolean checkHoliday() {
        return Weekday.from(localDateTime.getDayOfWeek()).equals(Weekday.SATURDAY) || Weekday.from(localDateTime.getDayOfWeek()).equals(Weekday.SUNDAY);
    }

    private boolean checkDefault() {
        return localDateTime.getHour() == 0;
    }

    private AttendanceStatus checkAttendanceStatus(String hourLimit) {
        int hourMinute = Integer.parseInt(localDateTime.getHour() + addZero(localDateTime.getMinute()));
        int absentTime = Integer.parseInt(hourLimit + AttendanceStatus.ABSENT_LIMIT);
        int lateTime = Integer.parseInt(hourLimit + AttendanceStatus.LATE_LIMIT);
        if (hourMinute > absentTime) {
            return AttendanceStatus.ABSENCE;
        }
        if (hourMinute > lateTime) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }
    private String addZero(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return String.valueOf(time);
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getAttendanceStatus() {
        return attendanceStatus.getName();
    }

}
