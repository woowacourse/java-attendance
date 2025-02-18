package domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {
    public static Object attend(LocalDateTime target) {
        int dayOfWeek = target.getDayOfWeek().getValue();

        LocalTime attendanceTime = LocalTime.of(10, 0);
        LocalTime targetTime = target.toLocalTime();

        if (dayOfWeek == 6 || dayOfWeek == 7) {
            throw new IllegalArgumentException();
        }

        if (dayOfWeek == 1) {
            attendanceTime = LocalTime.of(13, 0);
        }


        if (targetTime.isAfter(attendanceTime)) {
            if (attendanceTime.plusMinutes(30).isBefore(targetTime)) {
                return 2;
            }
            if (attendanceTime.plusMinutes(5).isBefore(targetTime)) {
                return 1;
            }
            return 0;
        }
        return 0;
    }
}
