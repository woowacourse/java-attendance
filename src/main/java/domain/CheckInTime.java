package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CheckInTime {
    private final LocalDateTime checkInTime;

    private CheckInTime(LocalDateTime checkInTime) {
        if (checkInTime.getDayOfWeek() == DayOfWeek.SATURDAY || checkInTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("[ERROR] 주말에는 출근할 수 없습니다.");
        }
        if (checkInTime.getDayOfMonth() == 25) {
            throw new IllegalArgumentException("[ERROR] 공휴일에는 출근할 수 없습니다.");
        }
        this.checkInTime = checkInTime;
    }

    public static CheckInTime of(LocalDateTime checkInTime) {
        return new CheckInTime(checkInTime);
    }

    public AttendanceStatus getAttendanceStatus() {
        LocalDateTime startTime = LocalDateTime.of(2024, 12, 10, 10, 0, 0);
        if(startTime.isAfter(checkInTime)) {
            return AttendanceStatus.PRESENCE;
        }

        int minute = Math.toIntExact(ChronoUnit.MINUTES.between(startTime, checkInTime));
        if(minute <= 5){
            return AttendanceStatus.PRESENCE;
        }
        if(minute <= 30) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ABSENCE;
    }
}
