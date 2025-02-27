package domain;

import static domain.December.DEFAULT_MONTH;
import static domain.December.DEFAULT_YEAR;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AttendTime {

    public static final String ABSENT = "결석";
    public static final String LATE = "지각";
    public static final String ATTENDED = "출석";
    public static final int LATE_TO_ABSENT_COUNT = 3;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private LocalDateTime attendTime;

    public AttendTime(String attendTime) {
        this.attendTime = LocalDateTime.parse(attendTime, FORMATTER);
    }

    public String checkAttendanceStatus() {
        final LocalDateTime lateTime = LocalDateTime.of(
                attendTime.getYear(), attendTime.getMonth(), attendTime.getDayOfMonth(),
                getDayInfo(attendTime.getDayOfMonth()), 5, 0);
        final LocalDateTime absentTime = LocalDateTime.of(
                attendTime.getYear(), attendTime.getMonth(), attendTime.getDayOfMonth(),
                getDayInfo(attendTime.getDayOfMonth()), 30, 0);
        if (attendTime.isAfter(absentTime)) {
            return ABSENT;
        }
        if (attendTime.isAfter(lateTime)) {
            return LATE;
        }
        return ATTENDED;
    }

    public AttendTime modifyAttendTime(final String time) {
        this.attendTime = LocalDateTime.parse(
                DEFAULT_YEAR + "-" + DEFAULT_MONTH + "-" + attendTime.getDayOfMonth() + " " + time, FORMATTER);
        return this;
    }

    public int getDayOfMonth() {
        return attendTime.getDayOfMonth();
    }

    public int getDayInfo(int dayOfMonth) {
        return StartTime.findStartTime(dayOfMonth);
    }

    public LocalDateTime getAttendTime() {
        return attendTime;
    }

}
