package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private static final int WEEKEND_HOLIDAY = 25;
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);

    private static final LocalTime MONDAY_ATTENDANCE_LIMIT = LocalTime.of(13, 5);
    private static final LocalTime MONDAY_LATE_LIMIT = LocalTime.of(13, 30);

    private static final LocalTime OTHER_DAYS_ATTENDANCE_LIMIT = LocalTime.of(10, 5);
    private static final LocalTime OTHER_DAYS_LATE_LIMIT = LocalTime.of(10, 30);

    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;
    private final String attendanceStatus;

    public Attendance(LocalDateTime attendanceDateTime) {
        validateAttendanceDate(attendanceDateTime.toLocalDate());
        validateAttendanceTime(attendanceDateTime.toLocalTime());
        this.attendanceDate = attendanceDateTime.toLocalDate();
        this.attendanceTime = attendanceDateTime.toLocalTime();
        this.attendanceStatus = determineAttendanceStatus();
    }

    private void validateAttendanceDate(LocalDate localDate) {
        if (localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || localDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)
            || localDate.getDayOfMonth() == WEEKEND_HOLIDAY) {
            throw new IllegalArgumentException("주말 및 공휴일은 출석을 받지않습니다");
        }
    }

    private void validateAttendanceTime(LocalTime localTime) {
        if (localTime.isBefore(START_TIME) || localTime.isAfter(END_TIME)) {
            throw new IllegalArgumentException("[ERROR] 지정된 시간이 아니면 등교가 불가능합니다.");
        }
    }

    private String determineAttendanceStatus() {
        if (attendanceDate.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return determineAttendance(MONDAY_ATTENDANCE_LIMIT,MONDAY_LATE_LIMIT);
        }
        return determineAttendance(OTHER_DAYS_ATTENDANCE_LIMIT,OTHER_DAYS_LATE_LIMIT);
    }

    private String determineAttendance(LocalTime attendanceLimit, LocalTime lateLimit) {
        if (attendanceTime.isBefore(attendanceLimit) || attendanceTime.equals(attendanceLimit)) {
            return Subject.ATTENDANCE.getStatus();
        }
        if (attendanceTime.isBefore(lateLimit) || attendanceTime.equals(lateLimit)) {
            return Subject.LATE.getStatus();
        }
        return Subject.ABSENT.getStatus();
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }
}
