package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AttendTime {
    private final LocalDateTime attendTime;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public AttendTime(String attendTime) {
        this.attendTime = LocalDateTime.parse(attendTime, FORMATTER);
    }

    public String checkTime() {
        final LocalDateTime lateTime = LocalDateTime.of(2024, 12, attendTime.getDayOfMonth(),
                getDayInfo(attendTime.getDayOfMonth()), 5, 0);
        final LocalDateTime absentTime = LocalDateTime.of(2024, 12, attendTime.getDayOfMonth(),
                getDayInfo(attendTime.getDayOfMonth()), 30, 0);
        if (attendTime.isAfter(absentTime)) {
            return "결석";
        }
        if (attendTime.isAfter(lateTime)) {
            return "지각";
        }

        return "출석";
    }

    public int getDayInfo(int dayOfMonth) {
        return StartTime.findStartTime(dayOfMonth);
    }

    public LocalDateTime getAttendTime() {
        return attendTime;
    }
}
